package aoc2023;

import common.DayBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day2 extends DayBase<List<Day2.Game>, Integer, Integer> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }

    record CubeSet(int red, int green, int blue) {
        public Boolean isValid(CubeSet bag) {
            return red <= bag.red && green <= bag.green && blue <= bag.blue;
        }
    }
    record Game(int id, List<CubeSet> sets) {
        public Boolean isValid(CubeSet bag) {
            return sets.stream().allMatch(s -> s.isValid(bag));
        }

        public CubeSet optimized() {
            return new CubeSet(
                    sets.stream().mapToInt(CubeSet::red).max().orElseThrow(),
                    sets.stream().mapToInt(CubeSet::green).max().orElseThrow(),
                    sets.stream().mapToInt(CubeSet::blue).max().orElseThrow()
            );
        }
    }


    @Override
    public Integer firstStar() {
        List<Game> games = this.getInput(Day2::parseGames);

        CubeSet bag = new CubeSet(12, 13, 14);
        return games.stream()
                .filter(g -> g.isValid(bag))
                .mapToInt(Game::id)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Game> games = this.getInput(Day2::parseGames);

        return games.stream()
                .map(Game::optimized)
                .mapToInt(cs -> cs.red() * cs.green() * cs.blue())
                .sum();
    }

    private static List<Game> parseGames(List<String> input) {
        return input.stream()
                .map(l -> new Game(extractGameId(l), extractSets(l)))
                .toList();
    }
    private static int extractGameId(String game) {
        return Integer.parseInt(game.split(": ")[0].split(" ")[1]);
    }
    private static List<CubeSet> extractSets(String game) {
        return Arrays.stream(game.split(": ")[1].split("; "))
                .map(s -> Arrays.stream(s.split(", "))
                        .map(c -> c.split(" "))
                        .collect(Collectors.toMap(c -> c[1], c -> Integer.parseInt(c[0]))))
                .map(s -> new CubeSet(
                        s.getOrDefault("red", 0),
                        s.getOrDefault("green", 0),
                        s.getOrDefault("blue", 0)
                ))
                .toList();
    }
}
