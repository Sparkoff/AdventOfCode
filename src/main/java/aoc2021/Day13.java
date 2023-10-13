package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day13 extends DayBase<Day13.Origami, Integer, String> {

    private static final String O = """
            #####
            #...#
            #...#
            #...#
            #####""";
    private static final String RHALRCRA = """
            ###..#..#..##..#....###...##..###...##.
            #..#.#..#.#..#.#....#..#.#..#.#..#.#..#
            #..#.####.#..#.#....#..#.#....#..#.#..#
            ###..#..#.####.#....###..#....###..####
            #.#..#..#.#..#.#....#.#..#..#.#.#..#..#
            #..#.#..#.#..#.####.#..#..##..#..#.#..#""";

    public Day13() {
        super();
    }

    public Day13(List<String> input) {
        super(input);
    }

    record Dot(int x, int y) {}

    enum FoldAxis {
        X, Y
    }

    record Fold(int value, FoldAxis axis) {}

    record Origami(List<Dot> dots, List<Fold> folds) {}

    @Override
    public Integer firstStar() {
        Origami origami = this.getInput(Day13::parseOrigami);

        return foldPaper(origami.dots, origami.folds.get(0)).size();
    }

    @Override
    public String secondStar() {
        Origami origami = this.getInput(Day13::parseOrigami);

        List<Dot> dots = origami.dots;
        for (Fold fold : origami.folds) {
            dots = foldPaper(dots, fold);
        }

        int xmin = dots.get(0).x;
        int xmax = dots.get(0).x;
        int ymin = dots.get(0).y;
        int ymax = dots.get(0).y;

        for (Dot dot : dots) {
            if (dot.x < xmin) {
                xmin = dot.x;
            } else if (dot.x > xmax) {
                xmax = dot.x;
            }
            if (dot.y < ymin) {
                ymin = dot.y;
            } else if (dot.y > ymax) {
                ymax = dot.y;
            }
        }

        int finalXmin = xmin;
        int finalXmax = xmax;
        List<List<String>> paper = IntStream.rangeClosed(ymin, ymax)
                .mapToObj(i -> IntStream.rangeClosed(finalXmin, finalXmax)
                        .mapToObj(j -> ".")
                        .collect(Collectors.toList()))
                .toList();

        for (Dot dot : dots) {
            paper.get(dot.y).set(dot.x, "#");
        }

        String paperDraw = String.join(
                "\n",
                paper.stream()
                        .map(l -> String.join("", l))
                        .toList()
        );
        System.out.println(paperDraw);

        return switch (paperDraw) {
            case O -> "O";
            case RHALRCRA -> "RHALRCRA";
            default -> "error";
        };
    }

    private static List<Dot> foldPaper(List<Dot> dots, Fold fold) {
        return dots.stream()
                .map(d -> {
                    if (fold.axis == FoldAxis.X && d.x > fold.value) {  //  |0    x1#<---|f---#x
                        return new Dot(2 * fold.value - d.x, d.y);   //  x1 = x-2(x-f) = 2f-x
                    } else if (fold.axis == FoldAxis.Y && d.y > fold.value) {
                        return new Dot(d.x, 2 * fold.value - d.y);
                    }
                    return d;
                })
                .collect(Collectors.toSet())
                .stream()
                .toList();
    }

    private static Origami parseOrigami(List<String> input) {
        List<Dot> dots = new ArrayList<>();
        List<Fold> folds = new ArrayList<>();

        for (String line : input) {
            if (line.isEmpty()) continue;

            if (line.startsWith("fold")) {
                line = line.split(" ")[2];
                folds.add(new Fold(
                        Integer.parseInt(line.substring(2)),
                        line.startsWith("x") ? FoldAxis.X : FoldAxis.Y
                ));
            } else {
                List<Integer> pt = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .toList();
                dots.add(new Dot(pt.get(0), pt.get(1)));
            }
        }

        return new Origami(dots, folds);
    }
}