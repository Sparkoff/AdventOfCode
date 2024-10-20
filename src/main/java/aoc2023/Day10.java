package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Day10 extends DayBase<Day10.Sketch, Integer, Integer> {

    public Day10() {
        super();
    }

    public Day10(List<String> input) {
        super(input);
    }


    record Sketch(List<List<String>> map, int startX, int startY) {}
    enum Dir { UP, DOWN, LEFT, RIGHT }
    record Init(int x, int y, Dir to) {}
    record Pt(int x, int y) {
        List<Pt> around() {
            return List.of(
                    new Pt(x, y - 1),
                    new Pt(x + 1, y),
                    new Pt(x, y + 1),
                    new Pt(x - 1, y)
            );
        }
    }


    @Override
    public Integer firstStar() {
        Sketch sketch = this.getInput(Day10::parsePipes);

        List<List<String>> path = run(sketch);

        return path.stream()
                .flatMap(List::stream)
                .map(c -> c.equals("S") ? "1" : "0")
                .mapToInt(Integer::parseInt)
                .sum() / 2;
    }

    @Override
    public Integer secondStar() {
        Sketch sketch = this.getInput(Day10::parsePipes);

        // expand map
        Sketch expandedMap = expandMap(sketch);

        List<List<String>> path = run(expandedMap);

        // explore outer space
        List<List<String>> exploredMap = exploreSpaces(path);

        // collapse map
        List<List<String>> collapsedMap = collapseMap(exploredMap);

        return collapsedMap.stream()
                .flatMap(List::stream)
                .map(c -> c.equals("S") ? "0" : c)
                .mapToInt(Integer::parseInt)
                .sum();
    }


    private Init initPath(Sketch sketch) {
        int x = sketch.startX();
        int y = sketch.startY();
        Dir to;

        if (y != 0 && sketch.map().get(y - 1).get(x).equals("|")) {
            y--;
            to = Dir.UP;
        } else if (y != 0 && sketch.map().get(y - 1).get(x).equals("F")) {
            y--;
            to = Dir.RIGHT;
        } else if (y != 0 && sketch.map().get(y - 1).get(x).equals("7")) {
            y--;
            to = Dir.LEFT;
        } else if (x != sketch.map().get(y).size() - 1 && sketch.map().get(y).get(x + 1).equals("-")) {
            x++;
            to = Dir.RIGHT;
        } else if (x != sketch.map().get(y).size() - 1 && sketch.map().get(y).get(x + 1).equals("J")) {
            x++;
            to = Dir.UP;
        } else if (x != sketch.map().get(y).size() - 1 && sketch.map().get(y).get(x + 1).equals("7")) {
            x++;
            to = Dir.DOWN;
        } else if (y != sketch.map().size() - 1 && sketch.map().get(y + 1).get(x).equals("|")) {
            y++;
            to = Dir.DOWN;
        } else if (y != sketch.map().size() - 1 && sketch.map().get(y + 1).get(x).equals("J")) {
            y++;
            to = Dir.LEFT;
        } else if (y != sketch.map().size() - 1 && sketch.map().get(y + 1).get(x).equals("L")) {
            y++;
            to = Dir.RIGHT;
        } else if (x != 0 && sketch.map().get(y).get(x - 1).equals("-")) {
            x--;
            to = Dir.LEFT;
        } else if (x != 0 && sketch.map().get(y).get(x - 1).equals("L")) {
            x--;
            to = Dir.UP;
        } else if (x != 0 && sketch.map().get(y).get(x - 1).equals("F")) {
            x--;
            to = Dir.DOWN;
        } else {
            throw new RuntimeException("No starting pipe found");
        }

        return new Init(x, y, to);
    }

    private List<List<String>> run(Sketch sketch) {
        boolean stop = false;

        Init init = initPath(sketch);
        int x = init.x();
        int y = init.y();
        Dir to = init.to();

        // duplicate map
        List<List<String>> map = sketch.map.stream()
                .map(l -> (List<String>) new ArrayList<>(l))
                .toList();

        while (!stop) {
            map.get(y).set(x, "S");
            String next;
            switch (to) {
                case UP:
                    y--;
                    next = map.get(y).get(x);
                    if (next.equals("S")) {
                        stop = true;
                    } else if (next.equals("F")) {
                        to = Dir.RIGHT;
                    } else if (next.equals("7")) {
                        to = Dir.LEFT;
                    } else if (!next.equals("|")) {
                        throw new RuntimeException("Unexpected pipe on UP: " + next);
                    }
                    break;
                case DOWN:
                    y++;
                    next = map.get(y).get(x);
                    if (next.equals("S")) {
                        stop = true;
                    } else if (next.equals("L")) {
                        to = Dir.RIGHT;
                    } else if (next.equals("J")) {
                        to = Dir.LEFT;
                    } else if (!next.equals("|")) {
                        throw new RuntimeException("Unexpected pipe on DOWN: " + next);
                    }
                    break;
                case LEFT:
                    x--;
                    next = map.get(y).get(x);
                    if (next.equals("S")) {
                        stop = true;
                    } else if (next.equals("L")) {
                        to = Dir.UP;
                    } else if (next.equals("F")) {
                        to = Dir.DOWN;
                    } else if (!next.equals("-")) {
                        throw new RuntimeException("Unexpected pipe on LEFT: " + next);
                    }
                    break;
                case RIGHT:
                    x++;
                    next = map.get(y).get(x);
                    if (next.equals("S")) {
                        stop = true;
                    } else if (next.equals("J")) {
                        to = Dir.UP;
                    } else if (next.equals("7")) {
                        to = Dir.DOWN;
                    } else if (!next.equals("-")) {
                        throw new RuntimeException("Unexpected pipe on RIGHT: " + next);
                    }
                    break;
            }
        }

        return map;
    }

    private static Sketch expandMap(Sketch sketch) {
        List<List<String>> expandedMap = new ArrayList<>();

        // add empty line
        expandedMap.add(Collections.nCopies(sketch.map().get(0).size() * 2 + 2, "."));

        for (int y = 0; y < sketch.map().size(); y++) {
            List<String> row = sketch.map().get(y);
            List<String> newRow1 = new ArrayList<>();
            List<String> newRow2 = new ArrayList<>();

            // add empty column
            newRow1.add(".");
            newRow2.add(".");

            for (int x = 0; x < row.size(); x++) {
                String current = row.get(x);
                newRow1.add(current);

                if (current.equals("F") || current.equals("L") || current.equals("-")) {
                    newRow1.add("-");
                } else if (current.equals("S")) {
                    String next = row.get(x + 1);
                    if (next.equals("J") || next.equals("7") || next.equals("-")) {
                        newRow1.add("-");
                    } else {
                        newRow1.add(".");
                    }
                } else {
                    newRow1.add(".");
                }

                if (current.equals("F") || current.equals("7") || current.equals("|")) {
                    newRow2.add("|");
                } else if (current.equals("S")) {
                    String next = sketch.map().get(y + 1).get(x);
                    if (next.equals("J") || next.equals("L") || next.equals("|")) {
                        newRow2.add("|");
                    } else {
                        newRow2.add(".");
                    }
                } else {
                    newRow2.add(".");
                }

                newRow2.add(".");
            }

            // add empty column
            newRow1.add(".");
            newRow2.add(".");

            expandedMap.add(newRow1);
            expandedMap.add(newRow2);
        }

        // add empty line
        expandedMap.add(Collections.nCopies(sketch.map().get(0).size() * 2 + 2, "."));

        return new Sketch(expandedMap, sketch.startX() * 2 + 1, sketch.startY() * 2 + 1);
    }

    private List<List<String>> exploreSpaces(List<List<String>> map) {
        // clean map
        map = map.stream()
                .map(row -> row.stream()
                        .map(c -> c.equals("S") ? "S" : ".")
                        .collect(Collectors.toList()))
                .toList();

        List<Pt> toExplore = new ArrayList<>();
        toExplore.add(new Pt(0, 0));

        int maxY = map.size() - 1;
        int maxX = map.get(0).size() - 1;

        while (!toExplore.isEmpty()) {
            Pt next = toExplore.remove(0);
            if (!map.get(next.y()).get(next.x()).equals("0")) {
                map.get(next.y()).set(next.x(), "0");

                for (Pt around : next.around()) {
                    if (around.x() >= 0 && around.x() <= maxX && around.y() >= 0 && around.y() <= maxY) {
                        if (!map.get(around.y()).get(around.x()).equals("0")
                                && !map.get(around.y()).get(around.x()).equals("S")) {
                            toExplore.add(around);
                        }
                    }
                }
            }
        }

        // clean exploration
        map = map.stream()
                .map(row -> row.stream()
                        .map(c -> c.equals("S") || c.equals("0") ? c : "1")
                        .toList())
                .toList();

        return map;
    }

    private List<List<String>> collapseMap(List<List<String>> map) {
        List<List<String>> collapsedMap = new ArrayList<>();

        for (int y = 1; y < map.size(); y += 2) {
            List<String> newRow = new ArrayList<>();
            for (int x = 1; x < map.get(y).size(); x += 2) {
                newRow.add(map.get(y).get(x));
            }
            collapsedMap.add(newRow);
        }

        return collapsedMap;
    }


    private static Sketch parsePipes(List<String> input) {
        List<List<String>> map = input.stream()
                .map(l -> Arrays.stream(l.split(""))
                        .map(String::valueOf)
                        .toList())
                .toList();

        for (int y = 0; y < map.size(); y++) {
            for (int x = 0; x < map.get(y).size(); x++) {
                if (map.get(y).get(x).equals("S")) {
                    return new Sketch(map, x, y);
                }
            }
        }
        return null;
    }
}
