package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day12 extends DayBase<Map<Character, List<Day12.GardenPlot>>, Integer, Integer> {

    public Day12() {
        super();
    }

    public Day12(List<String> input) {
        super(input);
    }

    record Pt(int x, int y) {
        public List<Pt> adjacents() {
            return List.of(
                    new Pt(x, y + 1),
                    new Pt(x, y - 1),
                    new Pt(x + 1, y),
                    new Pt(x - 1, y)
            );
        }
        public List<Border> leftQuadri() {
            return List.of(
                    new Border(this, new Pt(x + 1, y)),
                    new Border(this, new Pt(x, y + 1)),
                    new Border(new Pt(x + 1, y + 1), new Pt(x + 1, y)),
                    new Border(new Pt(x + 1, y + 1), new Pt(x, y + 1))
            );
        }
        public List<Border> rightQuadri() {
            return List.of(
                    new Border(this, new Pt(x - 1, y)),
                    new Border(this, new Pt(x, y + 1)),
                    new Border(new Pt(x - 1, y + 1), new Pt(x - 1, y)),
                    new Border(new Pt(x - 1, y + 1), new Pt(x, y + 1))
            );
        }
    }
    record GardenPlot(List<Pt> plots, int area, int perimeter, int sides) {}
    record Border(Pt in, Pt out) {}


    @Override
    public Integer firstStar() {
        Map<Character, List<GardenPlot>> garden = this.getInput(Day12::parseGarden);

        return garden.values().stream()
                .mapToInt(gp -> gp.stream()
                        .mapToInt(p -> p.area() * p.perimeter())
                        .sum()
                )
                .sum();
    }

    @Override
    public Integer secondStar() {
        Map<Character, List<GardenPlot>> garden = this.getInput(Day12::parseGarden);

        return garden.values().stream()
                .mapToInt(gp -> gp.stream()
                        .mapToInt(p -> p.area() * p.sides())
                        .sum()
                )
                .sum();
    }


    private static Map<Character, List<GardenPlot>> parseGarden(List<String> input) {
        List<Pt> pts = IntStream.range(0, input.size())
                .mapToObj(y -> IntStream.range(0, input.get(y).length())
                        .mapToObj(x -> new Pt(x, y))
                        .toList()
                )
                .flatMap(List::stream)
                .collect(Collectors.toList());

        Map<Character, List<GardenPlot>> garden = new HashMap<>();
        while (!pts.isEmpty()) {
            Pt init = pts.remove(0);
            char label = input.get(init.y()).charAt(init.x());

            List<Pt> next = List.of(init);
            List<Pt> plots = new ArrayList<>(next);
            while (!next.isEmpty()) {
                next = next.stream()
                        .flatMap(p ->  p.adjacents().stream())
                        .distinct()
                        .filter(pts::contains)
                        .filter(p -> input.get(p.y()).charAt(p.x()) == label)
                        .filter(p -> !plots.contains(p))
                        .peek(pts::remove)
                        .toList();
                plots.addAll(next);

            }

            if (!garden.containsKey(label)) {
                garden.put(label, new ArrayList<>());
            }

            List<Border> borders = plots.stream()
                    .map(pin -> pin.adjacents().stream()
                            .filter(pa -> !plots.contains(pa))
                            .map(pout -> new Border(pin, pout))
                            .toList()
                    )
                    .flatMap(List::stream)
                    .toList();

            int area = plots.size();
            int perimeter = borders.size();

            Map<Pt, List<Pt>> innerBorders = borders.stream()
                    .collect(Collectors.groupingBy(Border::in, Collectors.mapping(Border::out, Collectors.toList())));
            Map<Pt, List<Pt>> outerBorders = borders.stream()
                    .collect(Collectors.groupingBy(Border::out, Collectors.mapping(Border::in, Collectors.toList())));

            // Count of sides = count of edges (turn inside our outsides).
            //   /!\ Beware : quadri-points (same edge shared by 2 parts of the same plot) are counted 4 times, but only 2 needed :
            //         remove 2 edges on calculus when encountering one.
            int edgesIn = innerBorders.values().stream()
                    .mapToInt(Day12::countEdges)
                    .sum();
            int edgesOut = outerBorders.values().stream()
                    .mapToInt(Day12::countEdges)
                    .sum();
            int quadriPoint = (int) (borders.stream()
                                .map(Border::in)
                                .distinct()
                                .filter(pt -> new HashSet<>(borders).containsAll(pt.leftQuadri()) ||
                                        new HashSet<>(borders).containsAll(pt.rightQuadri())
                                )
                                .count() * 2);

            garden.get(label).add(new GardenPlot(
                    plots,
                    area,
                    perimeter,
                    edgesIn + edgesOut - quadriPoint
            ));
        }

        return garden;
    }

    private static int countEdges(List<Pt> pts) {
        if (pts.size() == 1) {
            return 0;
        } else if (pts.size() == 3) {
            return 2;
        } else if (pts.size() == 4) {
            return 4;
        } else if (pts.get(0).x() == pts.get(1).x() || pts.get(0).y() == pts.get(1).y()) {
            return 0;
        } else {
            return 1;
        }
    }
}
