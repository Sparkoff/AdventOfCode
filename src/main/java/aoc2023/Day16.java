package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Day16 extends DayBase<Day16.Grid, Integer, Integer> {

    public Day16() {
        super();
    }

    public Day16(List<String> input) {
        super(input);
    }

    enum Optic { LEFT_MIRROR, RIGHT_MIRROR, VERTICAL_SPLITTER, HORIZONTAL_SPLITTER }
    enum Dir { UP, DOWN, LEFT, RIGHT }
    record Tile(int x, int y) {}
    record Laser(Tile tile, Dir dir) {
        public Laser(int x, int y, Dir dir) {
            this(new Tile(x, y), dir);
        }

        public Tile nextTile() {
            return switch (dir) {
                case UP -> new Tile(tile.x(), tile.y() - 1);
                case DOWN -> new Tile(tile.x(), tile.y() + 1);
                case RIGHT -> new Tile(tile.x() + 1, tile.y());
                case LEFT -> new Tile(tile.x() - 1, tile.y());
            };
        }

        public Laser next() {
            return new Laser(nextTile(), dir());
        }

        public Laser reverse() {
            return switch (dir) {
                case UP -> new Laser(tile, Dir.DOWN);
                case DOWN -> new Laser(tile, Dir.UP);
                case RIGHT -> new Laser(tile, Dir.LEFT);
                case LEFT -> new Laser(tile, Dir.RIGHT);
            };
        }
    }
    record Grid(Map<Tile, Optic> optics, int width, int height) {
        public boolean isIn(Tile tile) {
            return tile.x() >= 0 && tile.y() >= 0
                    && tile.x() < width && tile.y() < height;
        }
    }


    @Override
    public Integer firstStar() {
        Grid grid = this.getInput(Day16::parseGrid);

        return explore(grid, new Laser(0, 0, Dir.RIGHT)).size();
    }

    @Override
    public Integer secondStar() {
        Grid grid = this.getInput(Day16::parseGrid);

        List<Laser> beams = new ArrayList<>();
        for (int x = 0; x < grid.width(); x++) {
            beams.add(new Laser(x, 0, Dir.DOWN));
            beams.add(new Laser(x, grid.height() - 1, Dir.UP));
        }
        for (int y = 0; y < grid.width(); y++) {
            beams.add(new Laser(0, y, Dir.RIGHT));
            beams.add(new Laser(grid.width() - 1, y, Dir.LEFT));
        }

        return beams.stream()
                .mapToInt(l -> explore(grid, l).size())
                .max()
                .orElseThrow();
    }

    private List<Tile> explore(Grid grid, Laser startBeam) {
        Set<Tile> energizedTiles = new HashSet<>();

        List<Laser> nexts = new ArrayList<>();
        Set<Laser> explored = new HashSet<>();

        nexts.add(startBeam);
        energizedTiles.add(startBeam.tile());

        while (!nexts.isEmpty()) {
            Laser current = nexts.remove(0);

            if (explored.contains(current) || !grid.isIn(current.tile())) {
                continue;
            }
            energizedTiles.add(current.tile());

            Tile currentTile = current.tile();
            if (grid.optics().containsKey(currentTile)) {
                switch (grid.optics().get(currentTile)) {
                    case RIGHT_MIRROR:
                        switch (current.dir()) {
                            case UP -> nexts.add(new Laser(currentTile, Dir.RIGHT).next());
                            case DOWN -> nexts.add(new Laser(currentTile, Dir.LEFT).next());
                            case RIGHT -> nexts.add(new Laser(currentTile, Dir.UP).next());
                            case LEFT -> nexts.add(new Laser(currentTile, Dir.DOWN).next());
                        }
                        break;
                    case LEFT_MIRROR:
                        switch (current.dir()) {
                            case UP -> nexts.add(new Laser(currentTile, Dir.LEFT).next());
                            case DOWN -> nexts.add(new Laser(currentTile, Dir.RIGHT).next());
                            case RIGHT -> nexts.add(new Laser(currentTile, Dir.DOWN).next());
                            case LEFT -> nexts.add(new Laser(currentTile, Dir.UP).next());
                        }
                        break;
                    case VERTICAL_SPLITTER:
                        switch (current.dir()) {
                            case UP:
                            case DOWN:
                                nexts.add(current.next());
                                break;
                            case RIGHT:
                            case LEFT:
                                nexts.add(new Laser(currentTile, Dir.DOWN).next());
                                nexts.add(new Laser(currentTile, Dir.UP).next());
                                break;
                        }
                        break;
                    case HORIZONTAL_SPLITTER:
                        switch (current.dir()) {
                            case UP:
                            case DOWN:
                                nexts.add(new Laser(currentTile, Dir.RIGHT).next());
                                nexts.add(new Laser(currentTile, Dir.LEFT).next());
                                break;
                            case RIGHT:
                            case LEFT:
                                nexts.add(current.next());
                                break;
                        }
                        break;
                }
            } else {
                explored.add(current);
                explored.add(current.reverse());
                nexts.add(current.next());
            }
        }

        return energizedTiles.stream().toList();
    }


    private static Grid parseGrid(List<String> input) {
        Map<Tile, Optic> optics = new HashMap<>();

        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                Tile p = new Tile(x, y);
                switch (input.get(y).charAt(x)) {
                    case '/' -> optics.put(p, Optic.RIGHT_MIRROR);
                    case '\\' -> optics.put(p, Optic.LEFT_MIRROR);
                    case '|' -> optics.put(p, Optic.VERTICAL_SPLITTER);
                    case '-' -> optics.put(p, Optic.HORIZONTAL_SPLITTER);
                    default -> {}
                }
            }
        }

        return new Grid(optics, input.get(0).length(), input.size());
    }
}
