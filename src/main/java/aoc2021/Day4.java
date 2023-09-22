package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day4 extends DayBase<Day4.Bingo, Integer> {

    public Day4() {
        super();
    }

    public Day4(List<String> input) {
        super(input);
    }

    record Field(int value, int index, boolean marked) {
        public Field(int value, int index) {
            this(value, index, false);
        }

        public Field asMarked() {
            return new Field(this.value, this.index, true);
        }
    }

    record Bingo(List<Integer> draws, List<List<Field>> boards) {}

    @Override
    public Integer firstStar() {
        Bingo bingo = this.getInput(Day4::parseBingoSetup);

        List<List<Field>> boards = bingo.boards;

        for (int draw : bingo.draws) {
            for (List<Field> board : boards) {
                for (int i = 0; i < board.size(); i++) {
                    if (board.get(i).value == draw) {
                        board.set(i, board.get(i).asMarked());

                        int idx = i;
                        boolean winRow = board.stream()
                                .filter(f -> (f.index - f.index % 5) == (idx - idx % 5))
                                .allMatch(Field::marked);
                        boolean winColumn = board.stream()
                                .filter(f -> f.index % 5 == idx % 5)
                                .allMatch(Field::marked);

                        if (winRow || winColumn) {
                            return draw * board.stream()
                                    .filter(f -> !f.marked)
                                    .mapToInt(Field::value)
                                    .sum();
                        }
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public Integer secondStar() {
        Bingo bingo = this.getInput(Day4::parseBingoSetup);

        List<List<Field>> boards = bingo.boards;

        for (int draw : bingo.draws) {
            for (int i = 0; i < boards.size(); i++) {
                List<Field> board = boards.get(i);
                for (int j = 0; j < board.size(); j++) {
                    if (board.get(j).value == draw) {
                        board.set(j, board.get(j).asMarked());

                        int idx = j;
                        boolean winRow = board.stream()
                                .filter(f -> (f.index - f.index % 5) == (idx - idx % 5))
                                .allMatch(Field::marked);
                        boolean winColumn = board.stream()
                                .filter(f -> f.index % 5 == idx % 5)
                                .allMatch(Field::marked);

                        if (winRow || winColumn) {
                            if (boards.size() > 1) {
                                boards.remove(i);
                                i--;
                            } else {
                                return draw * board.stream()
                                        .filter(f -> !f.marked)
                                        .mapToInt(Field::value)
                                        .sum();
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private static Bingo parseBingoSetup(List<String> inputs) {
        List<String> extendedInputs = new ArrayList<>(inputs);
        extendedInputs.add("");

        List<Integer> draws = new ArrayList<>();
        List<List<Field>> boards = new ArrayList<>();
        List<String> rows = new ArrayList<>();
        for (String line : extendedInputs) {
            if (line.contains(",")) {
                draws = Arrays.stream(inputs.get(0).split(","))
                        .map(Integer::parseInt)
                        .toList();
                continue;
            }

            if (line.isEmpty()) {
                if (!rows.isEmpty()) {
                    List<Integer> fieldValues = rows.stream()
                            .map(l -> l.split(" "))
                            .flatMap(Arrays::stream)
                            .filter(v -> !v.isEmpty())
                            .map(Integer::parseInt)
                            .toList();
                    List<Field> fields = IntStream.range(0, fieldValues.size())
                            .mapToObj(i -> new Field(fieldValues.get(i), i))
                            .collect(Collectors.toList());
                    boards.add(fields);
                }
                rows = new ArrayList<>();
            } else {
                rows.add(line);
            }
        }

        return new Bingo(draws, boards);
    }
}
