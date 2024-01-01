package aoc2018;

import common.DayBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day9 extends DayBase<Day9.Settlement, Integer, Long> {

    public Day9() {
        super();
    }

    public Day9(List<String> input) {
        super(input);
    }

    record Settlement(int players, int endMarble) {}

    // class that represent a node from linked list :
    // ArrayList would be a nightmare here on performance and memory footprint
    static class Node {
        private final int value;
        private Node next, previous;

        public Node(int value) {
            this.value = value;
        }
        public Node() {
            value = 0;
            next = this;
            previous = this;
        }

        public int getValue() { return value; }

        public Node getNext() { return next; }
        public void setNext(Node next) { this.next = next; }

        public Node getPrevious() { return previous; }
        public void setPrevious(Node previous) { this.previous = previous; }
    }

    @Override
    public Integer firstStar() {
        Settlement settlement = this.getInput(Day9::parseSettlement);

        return runGame(settlement.players(), settlement.endMarble())
                .values().stream()
                .map(Math::toIntExact)
                .max(Integer::compareTo)
                .orElseThrow();
    }

    @Override
    public Long secondStar() {
        Settlement settlement = this.getInput(Day9::parseSettlement);

        return runGame(settlement.players(), settlement.endMarble() * 100)
                .values().stream()
                .max(Long::compareTo)
                .orElseThrow();
    }

    private static Map<Integer,Long> runGame(int players, int endMarble) {
        Node current = new Node();

        int marble = 0;
        int player = 0;
        Map<Integer,Long> score = new HashMap<>();

        while (marble <= endMarble) {
            marble++;
            player = (player % players) + 1;  // name first player as 1

            if (marble % 23 == 0) {
                // set current on final element (6 previous, as the 7th will be deleted)
                current = current.getPrevious()
                        .getPrevious()
                        .getPrevious()
                        .getPrevious()
                        .getPrevious()
                        .getPrevious();
                // node to be deleted = 7th
                Node toDelete = current.getPrevious();

                score.put(
                        player,
                        score.getOrDefault(player, 0L) +
                                marble +
                                toDelete.getValue()
                );

                // delete 7th node from the chain
                toDelete.getPrevious().setNext(current);
                current.setPrevious(toDelete.getPrevious());

                // delete all references from the 7th to the chain
                // (needed for garbage collector clean up -> footprint !!)
                toDelete.setPrevious(null);
                toDelete.setNext(null);
            } else {
                // increase chain index of 1, for simplicity purpose
                current = current.getNext();

                // create new node and insert in chain
                Node newNode = new Node(marble);
                newNode.setPrevious(current);
                newNode.setNext(current.getNext());

                // make the chain accept the new node
                current.getNext().setPrevious(newNode);
                current.setNext(newNode);

                current = newNode;
            }
        }

        return score;
    }

    private static Settlement parseSettlement(List<String> input) {
        Pattern instructionPattern = Pattern.compile("(\\d+)[a-z\\s;]+(\\d+)[a-z\\s]+");

        Matcher m = instructionPattern.matcher(input.get(0));
        if (!m.find()) throw new RuntimeException("Regex not matching for : " + input.get(0));
        return new Settlement(
                Integer.parseInt(m.group(1)),
                Integer.parseInt(m.group(2))
        );
    }
}
