package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class Day8 extends DayBase<Day8.MapDocument, Integer, Long> {

    public Day8() {
        super();
    }

    public Day8(List<String> input) {
        super(input);
    }

    record Path(String left, String right) {
        public String getNext(char dir) {
            return dir == 'L' ? left : right;
        }
    }
    record MapDocument(String direction, Map<String,Path> nodes) {
        public static String START = "AAA";
        public static String END = "ZZZ";
    }


    @Override
    public Integer firstStar() {
        MapDocument mapDocument = this.getInput(Day8::parseMap);

        String current = MapDocument.START;
        int dirIndex = 0;
        int step = 0;

        while (!current.equals(MapDocument.END)) {
            current = mapDocument.nodes().get(current).getNext(mapDocument.direction().charAt(dirIndex));
            dirIndex = (dirIndex + 1) % mapDocument.direction().length();
            step++;
        }

        return step;
    }

    @Override
    public Long secondStar() {
        MapDocument mapDocument = this.getInput(Day8::parseMap);

        List<String> currents = mapDocument.nodes().keySet().stream()
                .filter(n -> n.charAt(2) == 'A')
                .toList();
        List<Integer> steps = new ArrayList<>();

        for (String node : currents) {
            String current = node;
            int dirIndex = 0;
            int step = 0;

            while (current.charAt(2) != 'Z') {
                current = mapDocument.nodes().get(current).getNext(mapDocument.direction().charAt(dirIndex));
                dirIndex = (dirIndex + 1) % mapDocument.direction().length();
                step++;
            }
            steps.add(step);
        }

        return steps.stream().map(s -> (long) s).reduce(1L, Day8::ppcm);
    }

    private static long pgcd(long a, long b) {
        // Egyptian algorithm
        if (a < b) {
            a = a + b;
            b = a - b;
            a = a - b;
        }
        while(b != 0){
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }
    private static long ppcm(long a, long b) {
        return (a * b) / pgcd(a, b);
    }


    private static MapDocument parseMap(List<String> input) {
        String direction = input.get(0);

        Pattern pattern = Pattern.compile("([\\dA-Z]{3}) = \\(([\\dA-Z]{3}), ([\\dA-Z]{3})\\)");
        Map<String,Path> nodes = input.subList(2, input.size()).stream()
                .map(pattern::matcher)
                .filter(Matcher::find)
                .collect(Collectors.toMap(m-> m.group(1), m -> new Path(m.group(2), m.group(3))));

        return new MapDocument(direction, nodes);
    }
}
