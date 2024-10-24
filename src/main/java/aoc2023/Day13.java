package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;


public class Day13 extends DayBase<List<Day13.Pattern>, Integer, Integer> {

    public Day13() {
        super();
    }

    public Day13(List<String> input) {
        super(input);
    }

    record Pattern(List<String> rows) {
        Pattern transpose() {
            List<StringBuilder> newRows = new ArrayList<>();

            for (int y = 0; y < rows.size(); y++) {
                for (int x = 0; x < rows.get(0).length(); x++) {
                    if (y == 0) {
                        newRows.add(new StringBuilder());
                    }
                    newRows.get(x).append(rows.get(y).charAt(x));
                }
            }

            return new Pattern(newRows.stream()
                    .map(StringBuilder::toString)
                    .toList());
        }
    }


    @Override
    public Integer firstStar() {
        List<Pattern> patterns = this.getInput(Day13::parsePatterns);

        return patterns.stream()
                .mapToInt(p -> summarize(p, false))
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Pattern> patterns = this.getInput(Day13::parsePatterns);

        return patterns.stream()
                .mapToInt(p -> summarize(p, true))
                .sum();
    }

    private int summarize(Pattern p, boolean fixMode) {
        int note = searchMirror(p, fixMode);
        if (note != -1) {
            return note * 100;
        }

        note = searchMirror(p.transpose(), fixMode);
        if (note != -1) {
            return note;
        }
        throw new RuntimeException("No mirror");
    }

    private int searchMirror(Pattern p, boolean fixMode) {
        for (int y = 0; y < p.rows().size() - 1; y++) {
            int diffCount = diff(p.rows().get(y), p.rows().get(y + 1));
            if ((!fixMode && diffCount == 0) || (fixMode && diffCount <= 1)) {
                if (isMirror(p, y, fixMode)) {
                    return y + 1;
                }
            }
        }
        return -1;
    }

    private boolean isMirror(Pattern p, int y, boolean fixMode) {
        int up = y;
        int down = y + 1;
        int diffCount = 0;

        while (true) {
            if (up < 0 || down >= p.rows().size()) {
                return !fixMode || diffCount == 1;
            }

            diffCount += diff(p.rows().get(up), p.rows().get(down));

            if ((!fixMode && diffCount != 0) || (fixMode && diffCount > 1)) {
                return false;
            }

            up--;
            down++;
        }
    }

    private int diff(String a, String b) {
        int count = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
        }
        return count;
    }


    private static List<Pattern> parsePatterns(List<String> input) {
        List<Pattern> patterns = new ArrayList<>();

        List<String> pattern = new ArrayList<>();
        for (String row : input) {
            if (row.isEmpty()) {
                patterns.add(new Pattern(pattern));
                pattern = new ArrayList<>();
            } else {
                pattern.add(row);
            }
        }
        patterns.add(new Pattern(pattern));

        return patterns;
    }
}
