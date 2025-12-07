package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Day14 extends DayBase<String, String, Integer> {

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }


    @Override
    public String firstStar() {
        int cookingEnd = Integer.parseInt(this.getInput(PuzzleInput::asString));
        List<Integer> recipes = new ArrayList<>(cookingEnd + 10);
        recipes.add(3);
        recipes.add(7);

        int elf1Idx = 0;
        int elf2Idx = 1;

        while (recipes.size() < cookingEnd + 10) {
            int newRecipeSum = recipes.get(elf1Idx) + recipes.get(elf2Idx);

            if (newRecipeSum >= 10) {
                recipes.add(newRecipeSum / 10);
            }
            recipes.add(newRecipeSum % 10);

            elf1Idx = (elf1Idx + recipes.get(elf1Idx) + 1) % recipes.size();
            elf2Idx = (elf2Idx + recipes.get(elf2Idx) + 1) % recipes.size();
        }

        return recipes.subList(cookingEnd, cookingEnd + 10).stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    @Override
    public Integer secondStar() {
        String scoreSequence = this.getInput(PuzzleInput::asString);
        List<Integer> target = scoreSequence.chars().map(c -> c - '0').boxed().toList();

        List<Integer> recipes = new ArrayList<>();
        recipes.add(3);
        recipes.add(7);

        int elf1Idx = 0;
        int elf2Idx = 1;

        while (true) {
            int newRecipeSum = recipes.get(elf1Idx) + recipes.get(elf2Idx);

            if (newRecipeSum >= 10) {
                recipes.add(newRecipeSum / 10);
                if (checkMatch(recipes, target)) {
                    return recipes.size() - target.size();
                }
            }
            recipes.add(newRecipeSum % 10);
            if (checkMatch(recipes, target)) {
                return recipes.size() - target.size();
            }

            elf1Idx = (elf1Idx + recipes.get(elf1Idx) + 1) % recipes.size();
            elf2Idx = (elf2Idx + recipes.get(elf2Idx) + 1) % recipes.size();
        }
    }

    private boolean checkMatch(List<Integer> recipes, List<Integer> target) {
        if (recipes.size() < target.size()) {
            return false;
        }
        // Check if the end of the recipes list matches the target sequence
        return recipes.subList(recipes.size() - target.size(), recipes.size()).equals(target);
    }
}
