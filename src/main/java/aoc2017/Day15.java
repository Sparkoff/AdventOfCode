package aoc2017;

import common.DayBase;

import java.util.List;


public class Day15 extends DayBase<Day15.Generators, Integer> {

    public Day15() {
        super();
    }

    public Day15(List<String> input) {
        super(input);
    }

    record Generators(long a, long b) {
        public Generators computeNext(boolean useCriteria) {
            long nextA = a;
            do {
                nextA = (nextA * 16807) % 2147483647;
            } while (useCriteria && nextA % 4 != 0);

            long nextB = b;
            do {
                nextB = (nextB * 48271) % 2147483647;
            } while (useCriteria && nextB % 8 != 0);

            return new Generators(nextA, nextB);
        }

        public boolean matchingLowerBits() {
            return (a & 0xFFFF) == (b & 0xFFFF);
        }
    }

    @Override
    public Integer firstStar() {
        Generators init = this.getInput(Day15::parseGeneratorsInit);

        return judge(init, false);
    }

    @Override
    public Integer secondStar() {
        Generators init = this.getInput(Day15::parseGeneratorsInit);

        return judge(init, true);
    }

    private static int judge(Generators init, boolean useCriteria) {
        long limit = (long) (useCriteria ? 5E6 : 4E7);

        Generators currentState = init;
        int score = 0;
        for (int i = 0; i < limit; i++) {
            currentState = currentState.computeNext(useCriteria);

            if (currentState.matchingLowerBits()) {
                score++;
            }
        }

        return score;
    }

    private static Generators parseGeneratorsInit(List<String> input) {
        List<Long> init = input.stream()
                .map(l -> l.split(" "))
                .map(l -> l[l.length - 1])
                .map(Long::parseLong)
                .toList();

        return new Generators(init.get(0), init.get(1));
    }
}
