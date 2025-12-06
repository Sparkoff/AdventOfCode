package aoc2025;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;


public class Day5 extends DayBase<Day5.Ingredients, Integer, Long> {

    public Day5() {
        super();
    }

    public Day5(List<String> input) {
        super(input);
    }


    record FreshRange(long min, long max) {
        FreshRange(long min, long max) {
            this.min = Math.min(min, max);
            this.max = Math.max(min, max);
        }

        public boolean freshness(long ingredientId) {
            return ingredientId >= min && ingredientId <= max;
        }

        public boolean overlap(FreshRange other) {
            return (other.min >= min && other.max <= max) ||
                    (other.min <= min && other.max >= max) ||
                    (other.min <= max && other.max >= min);
        }
    }
    record Ingredients(List<Long> ids, List<FreshRange> freshRanges) {}


    @Override
    public Integer firstStar() {
        Ingredients ingredients = this.getInput(Day5::parseIngredients);

        return Math.toIntExact(ingredients.ids().stream()
                .filter(id -> ingredients.freshRanges().stream()
                        .anyMatch(freshRange -> freshRange.freshness(id))
                )
                .count());
    }

    @Override
    public Long secondStar() {
        Ingredients ingredients = this.getInput(Day5::parseIngredients);

        List<FreshRange> freshRanges = new ArrayList<>(ingredients.freshRanges());

        boolean updated = true;
        while (updated) {
            updated = false;
            for (int i = 0; i < freshRanges.size() - 1; i++) {
                for (int j = i + 1; j < freshRanges.size(); j++) {
                    FreshRange first = freshRanges.get(i);
                    FreshRange second = freshRanges.get(j);

                    if (first.overlap(second)) {
                        freshRanges.remove(j);
                        freshRanges.remove(i);
                        freshRanges.add(new FreshRange(
                                Math.min(first.min(), second.min()),
                                Math.max(first.max(), second.max())
                        ));
                        updated = true;
                        break;
                    }
                }
            }
        }

        return freshRanges.stream()
                .mapToLong(freshRange -> freshRange.max() - freshRange.min() + 1)
                .sum();
    }


    private static Ingredients parseIngredients(List<String> input) {
        List<Long> ids = new ArrayList<>();
        List<FreshRange> freshRanges = new ArrayList<>();

        for (String line : input) {
            if (line.isEmpty()) continue;

            if (line.contains("-")) {
                String[] ends = line.split("-");
                freshRanges.add(new FreshRange(Long.parseLong(ends[0]), Long.parseLong(ends[1])));
            } else {
                ids.add(Long.parseLong(line));
            }
        }

        return new Ingredients(ids, freshRanges);
    }
}
