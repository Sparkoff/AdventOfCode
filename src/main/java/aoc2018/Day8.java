package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day8 extends DayBase<Day8.Node, Integer, Integer> {

    public Day8() {
        super();
    }

    public Day8(List<String> input) {
        super(input);
    }

    record Node(List<Node> children, List<Integer> metadata) {
        public int firstCheck() {
            return sumMetadata() + children.stream().mapToInt(Node::firstCheck).sum();
        }

        public int secondCheck() {
            if (children.isEmpty()) {
                return sumMetadata();
            }

            return metadata.stream()
                    .filter(m -> m <= children.size())
                    .mapToInt(m -> children.get(m - 1).secondCheck())
                    .sum();
        }

        private int sumMetadata() {
            return metadata.stream()
                    .mapToInt(m -> m)
                    .sum();
        }
    }


    @Override
    public Integer firstStar() {
        Node root = this.getInput(Day8::parseNode);

        return root.firstCheck();
    }

    @Override
    public Integer secondStar() {
        Node root = this.getInput(Day8::parseNode);

        return root.secondCheck();
    }

    private static Node parseNode(List<String> input) {
        return extractNode(
                Arrays.stream(input.get(0).split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList())
        );
    }

    private static Node extractNode(List<Integer> data) {
        int childCount = data.remove(0);
        int metadataCount = data.remove(0);

        List<Node> children = new ArrayList<>();
        List<Integer> metadata = new ArrayList<>();

        for (int i = 0; i < childCount; i++) {
            children.add(extractNode(data));
        }
        for (int i = 0; i < metadataCount; i++) {
            metadata.add(data.remove(0));
        }

        return new Node(children, metadata);
    }
}
