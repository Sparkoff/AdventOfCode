package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day5 extends DayBase<Day5.Almanac, Long, Long> {

    public Day5() {
        super();
    }

    public Day5(List<String> input) {
        super(input);
    }

    record Range(long begin, long end) {}
    record GuessedRange(List<Range> notFound, List<Range> found) {}
    record Conversion(long source, long destination, long range) {
        public long convert(long input) {
            if (input >= source && input <= source + range - 1) {
                return destination + input - source;
            }
            return -1L;
        }
        public GuessedRange convert(GuessedRange input) {
            List<Range> notFound = new ArrayList<>();
            List<Range> found = new ArrayList<>(input.found());

            for (Range current : input.notFound()) {
                if (current.end() < source || current.begin() > source + range - 1) {
                    notFound.add(current);
                } else if (current.begin() < source && current.end() <= source + range - 1) {
                    notFound.add(new Range(current.begin(), source - 1));
                    found.add(new Range(destination, destination + current.end() - source));
                } else if (current.begin() >= source && current.end() > source + range - 1) {
                    found.add(new Range(destination + current.begin() - source, destination + range - 1));
                    notFound.add(new Range(source + range, current.end()));
                } else if (current.begin() < source && current.end() > source + range - 1) {
                    notFound.add(new Range(current.begin(), source - 1));
                    found.add(new Range(destination, destination + range - 1));
                    notFound.add(new Range(source + range, current.end()));
                } else {
                    found.add(new Range(destination + current.begin() - source, destination + current.end() - source));
                }
            }

            return new GuessedRange(notFound, found);
        }
    }
    record Mapping(String source, String destination, List<Conversion> conversions) {
        public long convert(long input) {
            long converted = conversions.stream()
                    .mapToLong(c -> c.convert(input))
                    .max()
                    .orElseThrow();
            return converted == -1 ? input : converted;
        }
        public List<Range> convert(List<Range> input) {
            GuessedRange guessedRange = new GuessedRange(input, List.of());
            for (Conversion conversion : conversions) {
                guessedRange = conversion.convert(guessedRange);
            }
            return Stream.concat(guessedRange.notFound().stream(), guessedRange.found().stream()).toList();
        }
    }
    record Almanac(List<Long> seeds, List<Mapping> mappings) {
        public List<Long> seedLocations() {
            List<Long> locations = new ArrayList<>();

            for (Long seed : seeds) {
                for (Mapping mapping : mappings) {
                    seed = mapping.convert(seed);
                }
                locations.add(seed);
            }

            return locations;
        }
        public List<Range> rangeSeedLocation() {
            List<Range> locations = toRangeSeed();

            for (Mapping mapping : mappings) {
                locations = mapping.convert(locations);
            }

            return locations;
        }
        private List<Range> toRangeSeed() {
            final AtomicInteger counter = new AtomicInteger(0);
            return seeds.stream()
                    .collect(
                            Collectors.groupingBy(item -> {
                                final int i = counter.getAndIncrement();
                                return (i % 2 == 0) ? i : i - 1;
                            })
                    )
                    .values().stream()
                    .map(a -> new Range(a.get(0), a.get(0) + a.get(1) - 1))
                    .collect(Collectors.toList());
        }
    }


    @Override
    public Long firstStar() {
        Almanac almanac = this.getInput(Day5::parseAlmanac);

        return almanac.seedLocations().stream()
                .mapToLong(sl -> sl)
                .min()
                .orElseThrow();
    }

    @Override
    public Long secondStar() {
        Almanac almanac = this.getInput(Day5::parseAlmanac);

        return almanac.rangeSeedLocation().stream()
                .mapToLong(Range::begin)
                .min()
                .orElseThrow();
    }

    private static Almanac parseAlmanac(List<String> input) {
        List<String> extendedInput = new ArrayList<>(input);
        extendedInput.add("");

        List<Long> seeds = Arrays.stream(extendedInput.remove(0).split(": ")[1].split(" "))
                .map(Long::parseLong)
                .toList();
        extendedInput.remove(0);

        List<Mapping> mappings = new ArrayList<>();

        String source = null;
        String destination = null;
        List<Conversion> conversions = null;
        while (!extendedInput.isEmpty()) {
            String line = extendedInput.remove(0);
            if (conversions == null) {
                String[] mapLabel = line.split(" ")[0].split("-");
                source = mapLabel[0];
                destination = mapLabel[2];
                conversions = new ArrayList<>();
            } else if (line.isEmpty()) {
                mappings.add(new Mapping(source, destination, conversions));
                source = null;
                destination = null;
                conversions = null;
            } else {
                List<Long> numbers = Arrays.stream(line.split(" "))
                        .map(Long::parseLong)
                        .toList();
                conversions.add(new Conversion(numbers.get(1), numbers.get(0), numbers.get(2)));
            }
        }

        return new Almanac(seeds, mappings);
    }
}
