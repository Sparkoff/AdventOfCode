package aoc2022;

import common.DayBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Day1 extends DayBase<List<Day1.Elf>, Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }

    record Elf(List<Integer> calories) {
        public Integer totalCalories() {
            return calories.stream()
                    .mapToInt(Integer::intValue)
                    .sum();
        }
    }


    @Override
    public Integer firstStar() {
        List<Elf> elves = this.getInput(Day1::parseCaloriesByElves);
        return elves.stream()
                .map(Elf::totalCalories)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    @Override
    public Integer secondStar() {
        List<Elf> elves = this.getInput(Day1::parseCaloriesByElves);
        return elves.stream()
                .map(Elf::totalCalories)
                .sorted(Comparator.reverseOrder())
                .limit(3L)
                .mapToInt(Integer::intValue)
                .sum();
    }


    private static List<Elf> parseCaloriesByElves(List<String> input) {
        List<String> extendedInput = new ArrayList<>(input);
        extendedInput.add("");

        List<Elf> elves = new ArrayList<>();

        List<Integer> calories = new ArrayList<>();
        for (String cal : extendedInput) {
            if (cal.equals("")) {
                elves.add(new Elf(calories));
                calories = new ArrayList<>();
            } else {
                calories.add(Integer.parseInt(cal));
            }
        }


        return elves;
    }
}
