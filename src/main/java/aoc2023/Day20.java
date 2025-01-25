package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Day20 extends DayBase<Map<String, Day20.Module>, Integer, Long> {

    public Day20() {
        super();
    }

    public Day20(List<String> input) {
        super(input);
    }

    enum State { ON, OFF }
    enum Pulse { LOW, HIGH }
    enum ModuleType { FLIP_FLOP, CONJUNCTION, INPUT, OUTPUT }
    record Signal(String from, String to, Pulse p) {}
    static class Module {
        private final String id;
        private final ModuleType type;
        private State state = State.OFF;
        private final Map<String, Pulse> parentPulse;
        private final List<String> children;

        public Module(String id, ModuleType type, List<String> parents, List<String> children) {
            this.id = id;
            this.type = type;
            this.children = children;

            parentPulse = parents.stream()
                    .collect(Collectors.toMap(p -> p, p -> Pulse.LOW));
        }

        public Module copy() {
            return new Module(id, type, parentPulse.keySet().stream().toList(), children);
        }

        public List<Signal> receive(String from, Pulse p) {
            return switch (type) {
                case INPUT -> children.stream()
                        .map(c -> new Signal(id, c, p))
                        .toList();
                case FLIP_FLOP -> {
                    if (p == Pulse.HIGH) {
                        yield List.of();
                    } else {
                        state = state == State.OFF ? State.ON : State.OFF;
                        Pulse finalP = state == State.OFF ? Pulse.LOW : Pulse.HIGH;
                        yield children.stream()
                                .map(c -> new Signal(id, c, finalP))
                                .toList();
                    }
                }
                case CONJUNCTION -> {
                    parentPulse.put(from, p);
                    Pulse finalP = parentPulse.values().stream()
                            .allMatch(rp -> rp == Pulse.HIGH) ? Pulse.LOW : Pulse.HIGH;
                    yield children.stream()
                            .map(c -> new Signal(id, c, finalP))
                            .toList();
                }
                default -> List.of();
            };
        }

        public ModuleType getType() {
            return type;
        }

        public State getState() {
            return state;
        }

        public List<String> getParents() {
            return parentPulse.keySet().stream().toList();
        }
    }


    @Override
    public Integer firstStar() {
        Map<String, Module> moduleConfig = this.getInput(Day20::parseModuleConfig);

        // Copy modules to keep original clean
        Map<String, Module> modules = moduleConfig.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().copy()));

        int steps = 0;
        List<Integer> lowPulses = new ArrayList<>();
        List<Integer> highPulses = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            List<Signal> signalQueue = new ArrayList<>();
            signalQueue.add(new Signal("button", "broadcaster", Pulse.LOW));

            int lowPulse = 0;
            int highPulse = 0;

            while (!signalQueue.isEmpty()) {
                Signal next = signalQueue.remove(0);

                if (next.p() == Pulse.LOW) {
                    lowPulse++;
                } else {
                    highPulse++;
                }

                signalQueue.addAll(modules.get(next.to()).receive(next.from(), next.p()));
            }

            lowPulses.add(lowPulse);
            highPulses.add(highPulse);
            steps++;

            if (modules.values().stream()
                    .filter(m -> m.getType() == ModuleType.FLIP_FLOP)
                    .allMatch(m -> m.getState() == State.OFF)) {
                break;
            }
        }

        int totalLow = lowPulses.stream().mapToInt(Integer::intValue).sum() * (1000 / steps)
                + IntStream.range(0, 1000 % steps).map(lowPulses::get).sum();
        int totalHigh = highPulses.stream().mapToInt(Integer::intValue).sum() * (1000 / steps)
                + IntStream.range(0, 1000 % steps).map(highPulses::get).sum();

        return totalLow * totalHigh;
    }

    @Override
    public Long secondStar() {
        Map<String, Module> moduleConfig = this.getInput(Day20::parseModuleConfig);

        // Copy modules to keep original clean
        Map<String, Module> modules = moduleConfig.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().copy()));

        int steps = 0;

        Map<String, Long> activators = modules.get("rx")
                .getParents()
                .stream()
                .map(p -> modules.get(p).getParents())
                .flatMap(List::stream)
                .collect(Collectors.toMap(p -> p, p -> 0L));

        while (activators.values().stream().anyMatch(s -> s == 0)) {
            List<Signal> signalQueue = new ArrayList<>();
            signalQueue.add(new Signal("button", "broadcaster", Pulse.LOW));
            steps++;

            while (!signalQueue.isEmpty()) {
                Signal next = signalQueue.remove(0);

                if (activators.containsKey(next.from()) && next.p() == Pulse.HIGH && activators.get(next.from()) == 0) {
                    activators.put(next.from(), (long) steps);
                }

                signalQueue.addAll(modules.get(next.to()).receive(next.from(), next.p()));
            }

        }

        return activators.values().stream()
                .reduce(1L, this::ppcm);
    }

    private long pgcd(long a, long b) {
        // Egyptian algorithm
        if (a < b) {
            a = a + b;
            b = a - b;
            a = a - b;
        }
        while(b != 0){
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
    private long ppcm(long a, long b) {
        return (a * b) / pgcd(a, b);
    }


    private static Map<String, Module> parseModuleConfig(List<String> input) {
        Map<String, ModuleType> types = new HashMap<>();
        Map<String, List<String>> parents = new HashMap<>();
        Map<String, List<String>> children = new HashMap<>();

        for (String raw : input) {
            String[] line = raw.split(" -> ");

            String id;
            if (line[0].equals("broadcaster")) {
                id = line[0];
                types.put(id, ModuleType.INPUT);
            } else {
                id = line[0].substring(1);
                types.put(id, line[0].charAt(0) == '%' ? ModuleType.FLIP_FLOP : ModuleType.CONJUNCTION);
            }

            String[] rawChildren = line[1].split(", ");
            children.put(id, List.of(rawChildren));
            for (String child : rawChildren) {
                if (!parents.containsKey(child)) {
                    parents.put(child, new ArrayList<>());
                }
                parents.get(child).add(id);
            }

        }

        Set<String> ids = Stream.concat(parents.keySet().stream(), children.keySet().stream())
                .collect(Collectors.toSet());

        Map<String, Module> modules = new HashMap<>();
        for (String id : ids) {
            modules.put(
                    id,
                    new Module(
                            id,
                            types.getOrDefault(id, ModuleType.OUTPUT),
                            parents.getOrDefault(id, List.of()),
                            children.getOrDefault(id, List.of())
                    )
            );
        }

        return modules;
    }
}
