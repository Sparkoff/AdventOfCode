package aoc2017;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Day21 extends DayBase<List<Day21.Rule>, Integer , Integer> {

    public Day21() {
        super();
    }

    public Day21(List<String> input) {
        super(input);
    }

    private static final List<List<Integer>> MASKS_2 = List.of(
            List.of(2, 0, 3, 1),
            List.of(3, 2, 1, 0),
            List.of(1, 3, 0, 2),
            List.of(1, 0, 3, 2),
            List.of(3, 1, 2, 0),
            List.of(2, 3, 0, 1),
            List.of(0, 2, 1, 3)
    );
    private static final List<List<Integer>> MASKS_3 = List.of(
            List.of(6, 3, 0, 7, 4, 1, 8, 5, 2),
            List.of(8, 7, 6, 5, 4, 3, 2, 1, 0),
            List.of(2, 5, 8, 1, 4, 7, 0, 3, 6),
            List.of(2, 1, 0, 5, 4, 3, 8, 7, 6),
            List.of(8, 5, 2, 7, 4, 1, 6, 3, 0),
            List.of(6, 7, 8, 3, 4, 5, 0, 1, 2),
            List.of(0, 3, 6, 1, 4, 7, 2, 5, 8)
    );
    private static final List<String> INIT_IMAGE = List.of(".#.", "..#", "###");

    record Pattern(int size, List<String> masks) {
        public static Pattern buildPattern(String mask) {
            List<String> masks = new ArrayList<>();
            masks.add(mask);

            for (List<Integer> ids : mask.length() == 4 ? MASKS_2 : MASKS_3) {
                StringBuilder variation = new StringBuilder();
                for (int id : ids) {
                    variation.append(mask.charAt(id));
                }
                masks.add(variation.toString());
            }

            return new Pattern(
                    mask.length() == 4 ? 2: 3,
                    masks.stream()
                            .distinct()
                            .toList()
            );
        }

        public boolean match(String image) {
            return masks.contains(image);
        }
    }
    record Rule(Pattern pattern, List<String> enhancement) {
        public List<String> enhance(String image) {
            return pattern.match(image) ? enhancement : null;
        }
    }

    @Override
    public Integer firstStar() {
        List<Rule> rules = this.getInput(Day21::parseRules);

        return pixelOnCount(rules.size() > 2 ?
                fractalArt(rules, 5):
                fractalArt(rules, 2)
        );
    }

    @Override
    public Integer secondStar() {
        List<Rule> rules = this.getInput(Day21::parseRules);

        return pixelOnCount(fractalArt(rules, 18));
    }

    private static int pixelOnCount(List<String> image) {
        return image.stream()
                .map(l -> List.of(l.split("")))
                .mapToInt(l -> (int) l.stream()
                        .filter(pixel -> pixel.equals("#"))
                        .count())
                .sum();
    }

    private static List<String> fractalArt(List<Rule> rules, int iterations) {
        List<String> image = INIT_IMAGE;
        for (int i = 0; i < iterations; i++) {
            image = nextImage(rules, image);
        }
        return image;
    }
    private static List<String> nextImage(List<Rule> rules, List<String> image) {
        List<String> nextImage = new ArrayList<>();
        int squareSize = (image.size() / 2) * 2 == image.size() ? 2 : 3;
        int gridSize = image.size() / squareSize;

        for (int y = 0; y < gridSize; y++) {
            List<StringBuilder> newRows = new ArrayList<>();

            for (int x = 0; x < gridSize; x++) {
                StringBuilder square = new StringBuilder();

                for (int i = 0; i < squareSize; i++) {
                    square.append(
                            image.get(squareSize * y + i),
                            squareSize * x,
                            squareSize * x + squareSize
                    );
                }

                List<String> enhancement = rules.stream()
                        .map(r -> r.enhance(square.toString()))
                        .filter(Objects::nonNull)
                        .findFirst()
                        .orElseThrow();

                if (newRows.isEmpty()) {
                    for (String row : enhancement) {
                        newRows.add(new StringBuilder(row));
                    }
                } else {
                    for (int i = 0; i < enhancement.size(); i++) {
                        newRows.get(i).append(enhancement.get(i));
                    }
                }
            }

            nextImage.addAll(newRows.stream()
                    .map(StringBuilder::toString)
                    .toList());
        }

        return nextImage;
    }

    private static List<Rule> parseRules(List<String> input) {
        return input.stream()
                .map(l -> l.split(" => "))
                .map(l -> new Rule(
                        Pattern.buildPattern(l[0].replace("/", "")),
                        List.of(l[1].split("/"))
                ))
                .toList();
    }
}
