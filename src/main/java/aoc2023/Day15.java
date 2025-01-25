package aoc2023;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class Day15 extends DayBase<String, Integer, Integer> {

    public Day15() {
        super();
    }

    public Day15(List<String> input) {
        super(input);
    }

    record Lens(String label, int focal) {
        public Lens(String label, char focal) {
            this(label, Integer.parseInt(String.valueOf(focal)));
        }
    }


    @Override
    public Integer firstStar() {
        String commands = this.getInput(PuzzleInput::asString);

        int sum = 0;
        int currentValue = 0;
        for (int i = 0; i < commands.length(); i++) {
            char c = commands.charAt(i);
            if (c == ',') {
                sum += currentValue;
                currentValue = 0;
            } else {
                currentValue = computeHash(currentValue, c);
            }
        }
        sum += currentValue;

        return sum;
    }

    @Override
    public Integer secondStar() {
        String commands = this.getInput(PuzzleInput::asString);

        Map<Integer, List<Lens>> boxes = new HashMap<>();

        StringBuilder label = new StringBuilder();
        int index = 0;
        for (int i = 0; i < commands.length(); i++) {
            char c = commands.charAt(i);
            if (c == ',') {
                label = new StringBuilder();
                index = 0;
            } else if (c == '-') {
                List<Lens> boxContent = boxes.getOrDefault(index, new ArrayList<>());
                removeLens(boxContent, label.toString());
                boxes.put(index, boxContent);
            } else if (c == '=') {
                Lens newLens = new Lens(label.toString(), commands.charAt(i + 1));
                List<Lens> boxContent = boxes.getOrDefault(index, new ArrayList<>());
                insertLens(boxContent, newLens);
                boxes.put(index, boxContent);
                i++;
            } else {
                index = computeHash(index, c);
                label.append(c);
            }
        }

        return boxes.entrySet().stream()
                .mapToInt(e -> (e.getKey() + 1) * IntStream.range(0, e.getValue().size())
                        .map(i -> (i + 1) * e.getValue().get(i).focal())
                        .sum())
                .sum();
    }

    private int computeHash(int value, char c) {
        return ((value + (int) c) * 17) % 256;
    }

    private void insertLens(List<Lens> lenses, Lens newLens) {
        for (int i = 0; i < lenses.size(); i++) {
            if (lenses.get(i).label().equals(newLens.label())) {
                lenses.set(i, newLens);
                return;
            }
        }
        lenses.add(newLens);
    }

    private void removeLens(List<Lens> lenses, String label) {
        for (int i = 0; i < lenses.size(); i++) {
            if (lenses.get(i).label().equals(label)) {
                lenses.remove(i);
                break;
            }
        }
    }
}
