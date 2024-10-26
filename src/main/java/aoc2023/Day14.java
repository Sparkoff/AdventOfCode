package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day14 extends DayBase<Day14.Reflector, Integer, Integer> {

    public Day14() {
        super();
    }

    public Day14(List<String> input) {
        super(input);
    }

    record Pt(int x, int y) {
        public Pt north() {
            return new Pt(x, y + 1);
        }
        public Pt west() {
            return new Pt(x - 1, y);
        }
        public Pt south() {
            return new Pt(x, y - 1);
        }
        public Pt east() {
            return new Pt(x + 1, y);
        }
    }
    enum Type { ROUND_ROCK, CUBE_ROCK, EMPTY }
    record Reflector(Map<Pt, Type> map, int width, int height) {
        public void print() {
            System.out.println("============");
            for (int y = height; y > 0; y--) {
                StringBuilder row = new StringBuilder();
                for (int x = 1; x <= width; x++) {
                    row.append(switch (map.get(new Pt(x, y))) {
                        case ROUND_ROCK -> "O";
                        case CUBE_ROCK -> "#";
                        default -> ".";
                    });
                }
                System.out.println(row);
            }
        }
    }


    @Override
    public Integer firstStar() {
        Reflector reflector = this.getInput(Day14::parseReflector);

        tiltNorth(reflector);

        return load(reflector.map());
    }

    @Override
    public Integer secondStar() {
        Reflector reflector = this.getInput(Day14::parseReflector);

        List<Integer> loads = new ArrayList<>();
        List<Integer> seenLoads = new ArrayList<>();
        int indexNoChange = -1;

        int i = 0;
        while (true) {
            spin(reflector);

            int currentLoad = load(reflector.map());
            loads.add(currentLoad);

            if (seenLoads.contains(currentLoad)) {
                if (indexNoChange == -1) {
                    indexNoChange = i;
                } else if (loads.size() > 3 * indexNoChange) {
                    // nothing has changed for a long time
                    break;
                }
            } else {
                seenLoads.add(currentLoad);
                indexNoChange = -1;
            }
            i++;
        }

        // extract loops from no changed index as we are sure to have loops there
        List<Integer> loops = loads.subList(indexNoChange, loads.size());
        // search for least present values : would be indicator of pattern size
        int weakness = loops.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                        .entrySet().stream()
                        .sorted((e1, e2) -> Math.toIntExact(e1.getValue() - e2.getValue()))
                        .map(Map.Entry::getKey)
                        .findFirst()
                        .orElseThrow();

        // search 2 firsts occurrence of the weakness to identify the pattern size
        int first = loops.indexOf(weakness);
        int second = first + loops.subList(first + 1, loops.size()).indexOf(weakness) + 1;
        int patternSize = second - first;

        // extract pattern
        List<Integer> pattern = loops.subList(0, patternSize);

        return pattern.get((int) ((1E9 - indexNoChange - 1) % patternSize));
    }

    private int load(Map<Pt, Type> map) {
        return map.entrySet().stream()
                .filter(e -> e.getValue() == Type.ROUND_ROCK)
                .map(Map.Entry::getKey)
                .mapToInt(Pt::y)
                .sum();
    }

    private void spin(Reflector reflector) {
        tiltNorth(reflector);
        tiltWest(reflector);
        tiltSouth(reflector);
        tiltEast(reflector);
    }

    private void tiltNorth(Reflector reflector) {
        for (int x = 1; x <= reflector.width(); x++) {
            for (int y = reflector.height(); y > 0; y--) {
                Pt current = new Pt(x, y);
                if (reflector.map().get(current) == Type.ROUND_ROCK) {
                    moveNorth(reflector.map(), current);
                }
            }
        }
    }
    private void tiltWest(Reflector reflector) {
        for (int y = reflector.height(); y > 0; y--) {
            for (int x = 1; x <= reflector.width(); x++) {
                Pt current = new Pt(x, y);
                if (reflector.map().get(current) == Type.ROUND_ROCK) {
                    moveWest(reflector.map(), current);
                }
            }
        }
    }
    private void tiltSouth(Reflector reflector) {
        for (int x = 1; x <= reflector.width(); x++) {
            for (int y = 1; y <= reflector.height(); y++) {
                Pt current = new Pt(x, y);
                if (reflector.map().get(current) == Type.ROUND_ROCK) {
                    moveSouth(reflector.map(), current);
                }
            }
        }
    }
    private void tiltEast(Reflector reflector) {
        for (int x = reflector.width(); x > 0; x--) {
            for (int y = reflector.height(); y > 0; y--) {
                Pt current = new Pt(x, y);
                if (reflector.map().get(current) == Type.ROUND_ROCK) {
                    moveEast(reflector.map(), current);
                }
            }
        }
    }

    private void moveNorth(Map<Pt, Type> map, Pt rock) {
        Pt next = rock.north();
        if (checkAndReplace(map, rock, next)) {
            moveNorth(map, next);
        }
    }
    private void moveWest(Map<Pt, Type> map, Pt rock) {
        Pt next = rock.west();
        if (checkAndReplace(map, rock, next)) {
            moveWest(map, next);
        }
    }
    private void moveSouth(Map<Pt, Type> map, Pt rock) {
        Pt next = rock.south();
        if (checkAndReplace(map, rock, next)) {
            moveSouth(map, next);
        }
    }
    private void moveEast(Map<Pt, Type> map, Pt rock) {
        Pt next = rock.east();
        if (checkAndReplace(map, rock, next)) {
            moveEast(map, next);
        }
    }
    private boolean checkAndReplace(Map<Pt, Type> map, Pt current, Pt next) {
        if (map.containsKey(next) && map.get(next) == Type.EMPTY) {
            map.put(next, Type.ROUND_ROCK);
            map.put(current, Type.EMPTY);
            return true;
        }
        return false;
    }


    private static Reflector parseReflector(List<String> input) {
        Map<Pt, Type> map = new HashMap<>();
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                Type type = switch (input.get(y).charAt(x)) {
                    case 'O' -> Type.ROUND_ROCK;
                    case '#' -> Type.CUBE_ROCK;
                    default -> Type.EMPTY;
                };
                map.put(new Pt(x + 1, input.size() - y), type); // origin : Pt(1, 1)
            }
        }
        return new Reflector(map, input.get(0).length(), input.size());
    }
}
