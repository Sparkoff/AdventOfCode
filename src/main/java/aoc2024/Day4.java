package aoc2024;

import common.DayBase;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Day4 extends DayBase<Day4.WordSearch, Integer, Integer> {

    public Day4() {
        super();
    }

    public Day4(List<String> input) {
        super(input);
    }

    record Pt(int x, int y) {}
    record WordSearch(List<String> map, int width, int height) {
        public WordSearch(List<String> map) {
            this(map, map.get(0).length(), map.size());
        }

        public List<String> getWord(Pt p) {
            return Stream.of(getH(p), getDR(p), getV(p), getDL(p))
                    .filter(w -> !w.isEmpty())
                    .toList();
        }
        private String getH(Pt p) {
            if (p.x() <= width - 4) {
                return map.get(p.y()).substring(p.x(), p.x() + 4);
            }
            return "";
        }
        private String getV(Pt p) {
            if (p.y() <= height - 4) {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    s.append(map.get(p.y() + i).charAt(p.x()));
                }
                return s.toString();
            }
            return "";
        }
        private String getDL(Pt p) {
            if (p.x() >= 3 && p.y() <= height - 4) {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    s.append(map.get(p.y() + i).charAt(p.x() - i));
                }
                return s.toString();
            }
            return "";
        }
        private String getDR(Pt p) {
            if (p.x() <= width - 4 && p.y() <= height - 4) {
                StringBuilder s = new StringBuilder();
                for (int i = 0; i < 4; i++) {
                    s.append(map.get(p.y() + i).charAt(p.x() + i));
                }
                return s.toString();
            }
            return "";
        }

        public List<String> getCross(Pt p) {
            if (p.x() >= 1 && p.x() < width - 1 && p.y() >= 1 && p.y() < height - 1) {
                StringBuilder s1 = new StringBuilder();
                StringBuilder s2 = new StringBuilder();
                for (int i = 0; i < 3; i++) {
                    s1.append(map.get(p.y() - 1 + i).charAt(p.x() - 1 + i));
                    s2.append(map.get(p.y() + 1 - i).charAt(p.x() - 1 + i));
                }
                return List.of(s1.toString(), s2.toString());
            }
            return List.of();
        }
    }


    @Override
    public Integer firstStar() {
        WordSearch wordSearch = this.getInput(Day4::parseWordSearch);

        return (int) IntStream.range(0, wordSearch.width())
                .mapToObj(x -> IntStream.range(0, wordSearch.height())
                        .mapToObj(y -> new Pt(x, y))
                        .toList()
                )
                .flatMap(List::stream)
                .map(wordSearch::getWord)
                .flatMap(List::stream)
                .filter(w -> w.equals("XMAS") || w.equals("SAMX"))
                .count();
    }

    @Override
    public Integer secondStar() {
        WordSearch wordSearch = this.getInput(Day4::parseWordSearch);

        return (int) IntStream.range(0, wordSearch.width())
                .mapToObj(x -> IntStream.range(0, wordSearch.height())
                        .mapToObj(y -> new Pt(x, y))
                        .toList()
                )
                .flatMap(List::stream)
                .map(wordSearch::getCross)
                .filter(c -> c.size() == 2)
                .filter(c -> c.stream().allMatch(w -> w.equals("MAS") || w.equals("SAM")))
                .count();
    }

    private static WordSearch parseWordSearch(List<String> input) {
        return new WordSearch(input);
    }
}
