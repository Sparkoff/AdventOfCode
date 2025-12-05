package aoc2025;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Day4 extends DayBase<Day4.Diagram, Integer, Integer> {

    public Day4() {
        super();
    }

    public Day4(List<String> input) {
        super(input);
    }

    record Diagram(Set<Integer> rolls, int width, int height) {
        public List<Integer> adjacents(int pt) {
            List<Integer> adjs = new ArrayList<>();
            for (int i = -width; i <= width; i += width) {
                for (int j = -1; j <= 1; j++) {
                    int adj = pt + i + j;
                    if (i == 0 && j == 0) continue;
                    if (Math.abs(pt % height - adj % height) > 1) continue;
                    if (adj < 0 || adj >= width * height) continue;
                    adjs.add(pt + i + j);
                }
            }
            return adjs;
        }

        public Map<Integer, List<Integer>> allAdjacents() {
            return rolls().stream()
                    .collect(
                            HashMap::new,
                            (map, roll) -> map.put(
                                    roll,
                                    adjacents(roll).stream()
                                            .filter(rolls::contains)
                                            .toList()
                            ),
                            HashMap::putAll
                    );

        }
    }


    @Override
    public Integer firstStar() {
        Diagram diagram = this.getInput(Day4::parseDiagram);

        Map<Integer, List<Integer>> adjacents = diagram.allAdjacents();

        return Math.toIntExact(diagram.rolls.stream()
                .map(adjacents::get)
                .filter(adjs -> adjs.size() < 4)
                .count());
    }

    @Override
    public Integer secondStar() {
        Diagram diagram = this.getInput(Day4::parseDiagram);

        Map<Integer, List<Integer>> adjacents = diagram.allAdjacents();

        Set<Integer> rolls = new HashSet<>(diagram.rolls());

        while (true) {
            List<Integer> removed = rolls.stream()
                    .filter(roll -> adjacents.get(roll).stream()
                            .filter(rolls::contains)
                            .count() < 4
                    )
                    .toList();

            if (removed.isEmpty()) break;

            removed.forEach(rolls::remove);
        }

        return diagram.rolls().size() - rolls.size();
    }


    private static Diagram parseDiagram(List<String> input) {
        int width = input.get(0).length();
        int height = input.size();

        List<Integer> rolls = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                if (input.get(i).charAt(j) == '@') {
                    rolls.add(i * width + j);
                }
            }
        }

        return new Diagram(new HashSet<>(rolls), width, height);
    }
}
