package aoc2018;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day20 extends DayBase<String, Integer, Integer> {

    public Day20() {
        super();
    }

    public Day20(List<String> input) {
        super(input);
    }

    record Room(int x, int y) {
        public Room() {
            this(0, 0);
        }

        public Room next(char c) {
            return switch (c) {
                case 'N' -> goNorth();
                case 'E' -> goEast();
                case 'S' -> goSouth();
                case 'W' -> goWest();
                default -> throw new IllegalStateException("Unexpected value: " + c);
            };
        }

        private Room goNorth() {
            return new Room(x + 1, y);
        }
        private Room goEast() {
            return new Room(x, y + 1);
        }
        private Room goSouth() {
            return new Room(x - 1, y);
        }
        private Room goWest() {
            return new Room(x, y - 1);
        }
    }
    record Walk(Room room, int distance) {
        public Walk() {
            this(new Room(), 0);
        }

        public Walk next(char c) {
            return new Walk(room.next(c), distance + 1);
        }
    }


    @Override
    public Integer firstStar() {
        String regex = this.getInput(PuzzleInput::asString);

        String map = regex.substring(1, regex.length() - 1);
        String deadEndPath = "\\([NESW]+\\|\\)";
        Pattern alternatePath = Pattern.compile("(\\([NESW|]+\\))");

        while (map.contains("(")) {
            map = map.replaceAll(deadEndPath, "");

            Matcher matcher = alternatePath.matcher(map);
            if (matcher.find()) {
                for (int i = 0; i < matcher.groupCount(); i++) {
                    String capture = matcher.group(i);
                    String longest = Arrays.stream(capture.substring(1, capture.length() - 1).split("\\|"))
                            .max(Comparator.comparingInt(String::length))
                            .orElseThrow();
                    map = map.replaceFirst(Pattern.quote(capture), longest);
                }
            }
        }

        return map.length();
    }

    @Override
    public Integer secondStar() {
        String regex = this.getInput(PuzzleInput::asString);

        List<Walk> alternates = new ArrayList<>();
        Walk current = new Walk();
        Map<Room,Integer> path = new HashMap<>();

        for (int i = 1; i < regex.length() - 1; i++) {
            char c = regex.charAt(i);

            if (c == '(') {
                // store current room as root of alternates path
                alternates.add(current);
            } else if (c == '|') {
                // an alternative path is ended, reset origin information before running next alternative
                current = alternates.get(alternates.size() - 1);
            } else if (c == ')') {
                // delete origin and restore state before alternates
                current = alternates.remove(alternates.size() - 1);
            } else {
                current = current.next(c);
                path.put(
                        current.room(),
                        Math.min(
                                path.getOrDefault(current.room(), Integer.MAX_VALUE),
                                current.distance()
                        )
                );
            }
        }

        return (int) path.values().stream()
                .filter(d -> d >= 1000)
                .count();
    }
}
