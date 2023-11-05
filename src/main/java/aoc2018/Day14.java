package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;


public class Day14 extends DayBase<String, String, Integer> {

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }

    static class LinkedNode {
        private LinkedNode previous = this;
        private LinkedNode next = this;
        private final int value;

        LinkedNode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public LinkedNode getPrevious() {
            return previous;
        }

        public LinkedNode getNext() {
            return next;
        }

        public static LinkedNode createLinkedNodesFrom(int values) {
            List<LinkedNode> singleValues = Arrays.stream(String.valueOf(values).split(""))
                    .map(Integer::parseInt)
                    .map(LinkedNode::new)
                    .toList();

            LinkedNode first = singleValues.get(0);
            for (int i = singleValues.size() - 1; i > 0; i--) {
                first.insert(singleValues.get(i));
            }

            return first;
        }

        public void insert(LinkedNode node) {
            node.previous.next = next;
            next.previous = node.previous;

            node.previous = this;
            next = node;
        }

        public LinkedNode move() {
            LinkedNode node = this;

            for (int i = 0; i < value + 1; i++) {
                node = node.next;
            }

            return node;
        }
    }


    @Override
    public String firstStar() {
        int cookingEnd = Integer.parseInt(this.getInput(PuzzleInput::asString));

        LinkedNode firstRecipe = LinkedNode.createLinkedNodesFrom(37);
        LinkedNode elf1 = firstRecipe;
        LinkedNode elf2 = firstRecipe.getNext();

        int recipeCount = 2;

        while (recipeCount < cookingEnd + 10) {
            int newRecipes = elf1.getValue() + elf2.getValue();
            firstRecipe.getPrevious().insert(LinkedNode.createLinkedNodesFrom(newRecipes));

            elf1 = elf1.move();
            elf2 = elf2.move();

            recipeCount += newRecipes < 10 ? 1 : 2;
        }

        if (recipeCount != cookingEnd + 10) {
            firstRecipe = firstRecipe.getPrevious();
        }

        return getSequence(firstRecipe, 10);
    }

    @Override
    public Integer secondStar() {
        String scoreSequence = this.getInput(PuzzleInput::asString);
        int seqLength = scoreSequence.length();

        LinkedNode firstRecipe = LinkedNode.createLinkedNodesFrom(37);
        LinkedNode elf1 = firstRecipe;
        LinkedNode elf2 = firstRecipe.getNext();

        int recipeCount = 2;

        while (true) {
            int newRecipes = elf1.getValue() + elf2.getValue();
            firstRecipe.getPrevious().insert(LinkedNode.createLinkedNodesFrom(newRecipes));

            elf1 = elf1.move();
            elf2 = elf2.move();

            if (newRecipes < 10) {
                recipeCount++;
                if (scoreSequence.equals(getSequence(firstRecipe, seqLength))) {
                    return recipeCount - seqLength;
                }
            } else {
                recipeCount += 2;
                if (scoreSequence.equals(getSequence(firstRecipe, seqLength))) {
                    return recipeCount - seqLength;
                } else if (scoreSequence.equals(getSequence(firstRecipe.getPrevious(), seqLength))) {
                    return recipeCount - seqLength - 1;
                }
            }
        }
    }

    private static String getSequence(LinkedNode node, int length) {
        LinkedNode iter = node;

        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < length; i++) {
            iter = iter.getPrevious();
            sequence.insert(0, iter.getValue());
        }

        return sequence.toString();
    }
}
