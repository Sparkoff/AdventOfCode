package aoc2025;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;


public class Day3 extends DayBase<List<String>, Integer, Long> {

    public Day3() {
        super();
    }

    public Day3(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<String> joltageRatings = this.getInput(PuzzleInput::asStringList);

        return (int) joltageRatings.stream()
                .mapToLong(joltageRating -> findLargestJoltage(joltageRating, 2))
                .sum();
    }

    @Override
    public Long secondStar() {
        List<String> joltageRatings = this.getInput(PuzzleInput::asStringList);

        return joltageRatings.stream()
                .mapToLong(joltageRating -> findLargestJoltage(joltageRating, 12))
                .sum();
    }


    private static long findLargestJoltage(String joltageRating, int joltageSize) {
        List<Integer> ratings = Arrays.stream(joltageRating.split(""))
                .map(Integer::parseInt)
                .toList();

        StringBuilder joltage = new StringBuilder();

        int lastIndex = -1;
        while (joltageSize != 0) {
            int currentJoltage = 0;
            int currentIndex = ratings.size() - joltageSize;

            for (int i = currentIndex; i > lastIndex; i--) {
                if (ratings.get(i) >= currentJoltage) {
                    currentJoltage = ratings.get(i);
                    currentIndex = i;
                }
            }

            joltage.append(currentJoltage);
            lastIndex = currentIndex;
            joltageSize--;
        }

        return Long.parseLong(joltage.toString());
    }
}
