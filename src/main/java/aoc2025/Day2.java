package aoc2025;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;


public class Day2 extends DayBase<List<Day2.IdRange>, Long, Long> {

    public Day2() {
        super();
    }

    public Day2(List<String> input) {
        super(input);
    }

    record IdRange(long begin, long end) {}
    record Sequence(int size, int count) {
        public int length() {
            return size * count;
        }
    }
    record Pattern(IdRange idRange, Sequence sequence, long min, long max) {}


    @Override
    public Long firstStar() {
        List<IdRange> idRanges = this.getInput(Day2::parseProducts);

        return idRanges.stream()
                .flatMap(idRange -> this.getPatterns(idRange).stream()
                        .filter(pattern -> pattern.sequence().count() == 2)  // limit to pattern made from 2 sequences
                        .flatMap(pattern -> searchInvalidIds(pattern).stream())
                        .distinct()  // multiple sequence can make same pattern : 22*3 and 222*2 can make 222222 pattern, but count only once
                )
                .mapToLong(Long::longValue)
                .sum();
    }

    @Override
    public Long secondStar() {
        List<IdRange> idRanges = this.getInput(Day2::parseProducts);

        return idRanges.stream()
                .flatMap(idRange -> this.getPatterns(idRange).stream()
                        .flatMap(pattern -> searchInvalidIds(pattern).stream())
                        .distinct()  // multiple sequence can make same pattern : 22*3 and 222*2 can make 222222 pattern, but count only once
                )
                .mapToLong(Long::longValue)
                .sum();
    }


    private static List<IdRange> parseProducts(List<String> input) {
        return Arrays.stream(input.get(0).split(","))
                .map(range -> {
                    String[] ids = range.split("-");
                    return new IdRange(Long.parseLong(ids[0]), Long.parseLong(ids[1]));
                })
                .toList();
    }

    private static List<Long> searchInvalidIds(Pattern pattern) {
        return LongStream.rangeClosed(pattern.min(), pattern.max())
                .mapToObj(String::valueOf)
                .map(id -> id.repeat(pattern.sequence().count()))  // make pattern from sequence
                .filter(id -> id.length() % pattern.sequence().count() == 0)  // remove pattern with leading zeros
                .map(Long::parseLong)
                .filter(id -> id >= pattern.idRange().begin() && id <= pattern.idRange().end())  // check pattern in range
                .toList();
    }

    private List<Sequence> extractPatterns(long id) {
        int size = (int) Math.log10(id) + 1;

        List<Sequence> sequences = new ArrayList<>();

        // sequence size, based on range end
        for (int i = 1; i <= (int) Math.floor((double) size / 2); i++) {
            // count, based on range begin
            for (int j = 2; j <= size / i; j++) {
                sequences.add(new Sequence(i, j));
            }
        }

        return sequences;
    }

    private long minPattern(long id, Sequence sequence) {
        String idString = String.valueOf(id);

        // by definition, pattern length is always <= id length
        if (idString.length() == sequence.length()) {
            return Long.parseLong(idString.substring(0, sequence.size()));
        } else {
            return Long.parseLong("1" + "0".repeat(sequence.size() - 1));
        }
    }
    private long maxPattern(long id, Sequence sequence) {
        String idString = String.valueOf(id);

        // by definition, pattern length is always <= id length
        if (idString.length() == sequence.length()) {
            return Long.parseLong(idString.substring(0, sequence.size()));
        } else {
            return Long.parseLong("9".repeat(sequence.size()));
        }
    }

    private List<Pattern> getPatterns(IdRange idRange) {
        return extractPatterns(idRange.end()).stream()
                .map(p -> new Pattern(
                        idRange,
                        p,
                        minPattern(idRange.begin(), p),
                        maxPattern(idRange.end(), p))
                )
                .filter(pattern -> pattern.min() <= pattern.max())  // remove invalid patterns
                .toList();
    }
}
