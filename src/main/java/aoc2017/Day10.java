package aoc2017;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day10 extends DayBase<String, String> {

    private int hashSize = 256;

    public Day10() {
        super();
    }

    public Day10(List<String> input) {
        super(input);
    }

    public void setHashSize(int hashSize) {
        this.hashSize = hashSize;
    }

    record Step(int currentIdx, int skip, List<Integer> hash) {
        public Step(List<Integer> hash) {
            this(0, 0, hash);
        }
    }

    @Override
    public String firstStar() {
        String lengths = this.getInput(PuzzleInput::asString);

        Step init = new Step(initHash());
        List<Integer> hash = computeHash(toListOfInt(lengths), init).hash();
        return String.valueOf(hash.get(0) * hash.get(1));
    }

    @Override
    public String secondStar() {
        String word = this.getInput(PuzzleInput::asString);
        List<Integer> asciiWord = toAsciiList(word);

        Step step = new Step(initHash());
        for (int i = 0; i < 64; i++) {
            step = computeHash(asciiWord, step);
        }

        List<Integer> hash = step.hash();
        return IntStream.range(0, hashSize)
                .boxed()
                .collect(Collectors.groupingBy(i -> i / 16))
                .values().stream()
                .map(idxs -> idxs.stream()
                        .map(hash::get)
                        .collect(Collectors.toList()))
                .map(this::toDense)
                .map(this::toHexa)
                .collect(Collectors.joining(""));

    }

    private List<Integer> initHash() {
        return IntStream.range(0, hashSize)
                .boxed()
                .collect(Collectors.toList());
    }
    private List<Integer> toListOfInt(String input) {
        return Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .toList();
    }
    private List<Integer> toAsciiList(String input) {
        List<Integer> suffix = List.of(17, 31, 73, 47, 23);

        if (input.isEmpty()) return suffix;

        List<Integer> ascii = Arrays.stream(input.split(""))
                .map(c -> (int) c.charAt(0))
                .collect(Collectors.toList());
        ascii.addAll(suffix);
        return ascii;
    }

    private Step computeHash(List<Integer> lengths, Step step) {
        for (int l : lengths) {
            List<Integer> hash = step.hash();
            if (l > 1) {
                int lengthMid = l / 2;

                for (int li = 0; li < lengthMid; li++) {
                    int firstIdx = circularIndex(step.currentIdx() + li);
                    int firstElement = hash.get(firstIdx);

                    int secondIdx = circularIndex(step.currentIdx() + l - 1 - li);
                    int secondElement = hash.get(secondIdx);

                    hash.set(firstIdx, secondElement);
                    hash.set(secondIdx, firstElement);
                }
            }

            step = new Step(
                    circularIndex(step.currentIdx() + l + step.skip()),
                    step.skip() + 1,
                    hash
            );
        }

        return step;
    }

    private int circularIndex(int idx) {
        return idx % hashSize;
    }
    private int toDense(List<Integer> hash) {
        return hash.stream().reduce(0, (a, c) -> a ^ c);
    }
    private String toHexa(int denseHash) {
        return String.format("%02x", denseHash);
    }
}
