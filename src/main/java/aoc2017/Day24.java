package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day24 extends DayBase<List<Day24.Component>, Integer, Integer> {

    public Day24() {
        super();
    }

    public Day24(List<String> input) {
        super(input);
    }

    record Component(int left, int right) {
        public int strength() {
            return left + right;
        }

        public boolean hasType(int type) {
            return left == type || right == type;
        }

        public int next(int type) {
            return left == type ? right : left;
        }
    }

    @Override
    public Integer firstStar() {
        List<Component> components = this.getInput(Day24::parseComponents);

        return makeBridges(components).stream()
                .mapToInt(b -> b.stream()
                        .mapToInt(Component::strength)
                        .sum())
                .max()
                .orElseThrow();
    }

    @Override
    public Integer secondStar() {
        List<Component> components = this.getInput(Day24::parseComponents);

        return makeBridges(components).stream()
                .collect(Collectors.groupingBy(List::size))

                .entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getKey))
                .orElseThrow()

                .getValue().stream()
                .mapToInt(b -> b.stream()
                        .mapToInt(Component::strength)
                        .sum())
                .max()
                .orElseThrow();
    }

    private static List<List<Component>> makeBridges(List<Component> components) {
        return findBridgesRecursive(components, new boolean[components.size()], 0);
    }

    private static List<List<Component>> findBridgesRecursive(List<Component> allComponents, boolean[] used, int nextPort) {
        List<List<Component>> bridges = new ArrayList<>();

        for (int i = 0; i < allComponents.size(); i++) {
            if (!used[i]) {
                Component component = allComponents.get(i);
                if (component.hasType(nextPort)) {
                    used[i] = true; // Mark as used for this path
                    bridges.add(List.of(component)); // This component alone forms a valid (short) bridge

                    List<List<Component>> subBridges = findBridgesRecursive(allComponents, used, component.next(nextPort));

                    for (List<Component> subBridge : subBridges) {
                        bridges.add(Stream.concat(Stream.of(component), subBridge.stream()).toList());
                    }

                    used[i] = false; // Backtrack: un-mark for other paths
                }
            }
        }
        return bridges;
    }

    private static List<Component> parseComponents(List<String> input) {
        return input.stream()
                .map(l -> l.split("/"))
                .map(l -> new Component(Integer.parseInt(l[0]), Integer.parseInt(l[1])))
                .toList();
    }
}
