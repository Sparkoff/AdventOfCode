package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day4 extends DayBase<List<Day4.Guard>, Integer, Integer> {

    public Day4() {
        super();
    }

    public Day4(List<String> input) {
        super(input);
    }

    record Log(int time, String text) {}
    record Asleep(int id, int time) {}
    record Guard(int id, Map<Integer, Integer> asleep) {}

    @Override
    public Integer firstStar() {
        List<Guard> guards = this.getInput(Day4::parseShifts);

        Guard guard = guards.stream()
                .max(Comparator.comparingInt(g -> g.asleep().values().stream().mapToInt(c -> c).sum()))
                .orElseThrow();

        return guard.id() * guard.asleep().entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    @Override
    public Integer secondStar() {
        List<Guard> guards = this.getInput(Day4::parseShifts);

        Guard guard = guards.stream()
                .max(Comparator.comparingInt(g -> Collections.max(g.asleep().values())))
                .orElseThrow();

        return guard.id() * guard.asleep().entrySet().stream()
                .max(Comparator.comparingInt(Map.Entry::getValue))
                .orElseThrow()
                .getKey();
    }

    private static List<Guard> parseShifts(List<String> input) {
        Pattern logPattern = Pattern.compile("\\[1518-\\d+-\\d+\\s\\d+:(\\d+)]\\s([\\w\\s#]+)");

        List<Log> logs = input.stream()
                .sorted()
                .map(l -> {
                    Matcher m = logPattern.matcher(l);
                    if (!m.find()) throw new RuntimeException("Regex not matching for : " + l);
                    return m;
                })
                .map(m -> new Log(
                        Integer.parseInt(m.group(1)),
                        m.group(2)
                ))
                .toList();

        Pattern shiftPattern = Pattern.compile("Guard\\s#(\\d+)\\sbegins\\sshift");
        int currentGuard = -1;
        int sleep = -1;
        List<Asleep> asleep = new ArrayList<>();
        for (Log log : logs){
            if (log.text().equals("falls asleep")) {
                sleep = log.time();
            } else if (log.text().equals("wakes up")) {
                int finalCurrentGuard = currentGuard;
                asleep.addAll(IntStream.range(sleep, log.time())
                        .boxed()
                        .map(t -> new Asleep(finalCurrentGuard, t))
                        .toList());
            } else {
                Matcher m = shiftPattern.matcher(log.text());
                if (!m.find()) throw new RuntimeException("Regex not matching for : " + log.text());
                currentGuard = Integer.parseInt(m.group(1));
            }
        }

        return asleep.stream()
                .collect(Collectors.groupingBy(
                        Asleep::id,
                        Collectors.groupingBy(
                                Asleep::time,
                                Collectors.counting()
                        )
                ))
                .entrySet().stream()
                .map(e -> new Guard(
                        e.getKey(),
                        e.getValue().entrySet().stream()
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        v -> Math.toIntExact(v.getValue())
                                ))
                ))
                .toList();
    }
}
