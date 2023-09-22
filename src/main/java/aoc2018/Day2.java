package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day2 extends DayBase<List<String>, String> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }

    record Checksum(boolean pair, boolean tuple) {}

    @Override
    public String firstStar() {
        List<String> boxIds = this.getInput(PuzzleInput::asStringList);

        List<Checksum> checksums = boxIds.stream()
                .map(id -> Arrays.stream(id.split(""))
                        .collect(Collectors.groupingBy(c -> c, Collectors.counting()))
                        .values())
                .map(freq -> new Checksum(freq.contains(2L), freq.contains(3L)))
                .toList();

        return String.valueOf(
                checksums.stream().filter(Checksum::pair).count() *
                        checksums.stream().filter(Checksum::tuple).count()
        );
    }

    @Override
    public String secondStar() {
        List<String> boxIds = this.getInput(PuzzleInput::asStringList);

        for (int id1 = 0; id1 < boxIds.size(); id1++) {
            for (int id2 = id1 + 1; id2 < boxIds.size(); id2++) {
                String w1 = boxIds.get(id1);
                String w2 = boxIds.get(id2);

                StringBuilder common = new StringBuilder();
                int distance = w1.length();
                for (int index = 0; index < w1.length(); index++) {
                    if (w1.charAt(index) == w2.charAt(index)) {
                        distance--;
                        common.append(w1.charAt(index));
                    }
                }
                if (distance == 1) {
                    return common.toString();
                }
            }
        }
        throw new RuntimeException("Boxes not found");
    }
}
