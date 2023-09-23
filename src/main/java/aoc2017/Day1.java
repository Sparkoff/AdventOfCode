package aoc2017;

import common.DayBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day1 extends DayBase<List<Integer>, Integer, Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }


    @Override
    public Integer firstStar() {
        List<Integer> captcha = this.getInput(Day1::parseCaptcha);
        captcha.add(captcha.get(0));

        return IntStream.range(0, captcha.size() - 1)
                .map(i -> (captcha.get(i).equals(captcha.get(i + 1))) ? captcha.get(i) : 0)
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Integer> captcha = this.getInput(Day1::parseCaptcha);

        int halfway = captcha.size() / 2;
        return IntStream.range(0, halfway)
                .map(i -> (captcha.get(i).equals(captcha.get(i + halfway))) ? captcha.get(i) : 0)
                .sum() * 2;
    }


    private static List<Integer> parseCaptcha(List<String> input) {
        return Arrays.stream(input.get(0).split(""))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
