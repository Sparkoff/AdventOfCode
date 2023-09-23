package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day7 extends DayBase<Day7.Program, String, Integer> {

    public Day7() {
        super();
    }

    public Day7(List<String> input) {
        super(input);
    }

    static class Program {
        private final String name;
        private final int size;
        private List<Program> children = new ArrayList<>();
        private boolean isBalanced = true;

        public Program(String name) {
            this.name = name;
            this.size = 0;
        }
        public Program(String name, int size) {
            this.name = name;
            this.size = size;
        }
        public Program(String name, int size, List<Program> children) {
            this.name = name;
            this.size = size;
            this.children = children;
            this.isBalanced = computeBalance();
        }

        public String getName() {
            return name;
        }
        public int getSize() {
            return size;
        }
        public List<Program> getChildren() {
            return children;
        }

        private int getWeight() {
            return size + children.stream()
                    .mapToInt(Program::getWeight)
                    .sum();
        }
        private boolean computeBalance() {
            if (children.isEmpty()) return true;

            return children.stream()
                    .map(Program::getWeight)
                    .distinct()
                    .count() == 1L;
        }

        public int getBalance() {
            if (isBalanced) return 0;

            Program unbalancedChild = children.stream()
                    .filter(p -> !p.isBalanced)
                    .findFirst()
                    .orElse(null);

            if (unbalancedChild != null) {
                // propagate balance analysis as one of the children marked as unbalanced
                return unbalancedChild.getBalance();
            } else {
                // current program is the deepest program marked as unbalanced,
                // so one of its children is making the unbalance

                // get list of child program weights, ordered by count
                // 1 program is different of the other
                List<Integer> weights = children.stream()
                        .map(Program::getWeight)
                        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .sorted(Comparator.comparingLong(Map.Entry::getValue))
                        .map(Map.Entry::getKey)
                        .toList();
                unbalancedChild = children.stream()
                        .filter(p -> p.getWeight() == weights.get(0))
                        .findFirst()
                        .orElseThrow();

                return unbalancedChild.size - weights.get(0) + weights.get(1);
            }
        }
    }

    @Override
    public String firstStar() {
        Program disk = this.getInput(Day7::parseDisk);

        return disk.getName();
    }

    @Override
    public Integer secondStar() {
        Program disk = this.getInput(Day7::parseDisk);

        return disk.getBalance();
    }

    private static Program parseDisk(List<String> input) {
        Map<String,Program> programs = input.stream()
                .map(Day7::parseDiskRow)
                .collect(Collectors.toMap(Program::getName, Function.identity()));

        // list of parent program names
        List<String> parents = programs.entrySet().stream()
                .filter(p -> !p.getValue().getChildren().isEmpty())
                .map(Map.Entry::getKey)
                .toList();
        // list of child program names
        List<String> children = programs.values().stream()
                .map(Program::getChildren)
                .flatMap(List::stream)
                .map(Program::getName)
                .distinct()
                .toList();

        // filter all parent programs that are also child of another one -> top parent program name
        String disk = parents.stream()
                .filter(p -> !children.contains(p))
                .findFirst()
                .orElseThrow();

        return getProgramTree(disk, programs);
    }
    private static Program parseDiskRow(String row) {
        String[] info = row.split(" -> ");
        String[] data = info[0].split(" ");
        int size = Integer.parseInt(data[1].substring(1, data[1].length() - 1));

        if (info.length == 2) {
            return new Program(
                    data[0],
                    size,
                    Arrays.stream(info[1].split(", "))
                            .map(Program::new)
                            .toList()
            );
        } else {
            return new Program(data[0], size);
        }
    }
    private static Program getProgramTree(String name, Map<String,Program> programList) {
        Program program = programList.get(name);
        if (!programList.get(name).getChildren().isEmpty()) {
            return new Program(
                    program.getName(),
                    program.getSize(),
                    program.getChildren().stream()
                            .map(child -> getProgramTree(child.getName(), programList))
                            .toList()
            );
        }
        return program;
    }
}
