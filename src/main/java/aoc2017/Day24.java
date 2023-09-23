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

        return makeBridges(components, 0).stream()
                .mapToInt(b -> b.stream()
                        .mapToInt(Component::strength)
                        .sum())
                .max()
                .orElseThrow();
    }

    @Override
    public Integer secondStar() {
        List<Component> components = this.getInput(Day24::parseComponents);

        return makeBridges(components, 0).stream()
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

    private static List<List<Component>> makeBridges(List<Component> availableComponents,  int nextType) {
        List<Component> nextComponents = availableComponents.stream()
                .filter(c -> c.hasType(nextType))
                .toList();

        List<List<Component>> bridges = new ArrayList<>();
        for (Component component : nextComponents) {
            bridges.add(List.of(component));
            List<List<Component>> nextBridges = makeBridges(
                    availableComponents.stream()
                            .filter(c -> c != component)
                            .toList(),
                    component.next(nextType)
            );
            if (!nextBridges.isEmpty()) {
                bridges.addAll(nextBridges.stream()
                        .map(b -> Stream.concat(Stream.of(component), b.stream()).toList())
                        .toList());
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
