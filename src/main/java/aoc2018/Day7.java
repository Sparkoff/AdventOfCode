package aoc2018;

import common.DayBase;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Day7 extends DayBase<Day7.Tasks, String, Integer> {

    private final String TASKS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int basicTime = 60;
    private int workers = 5;

    public Day7() {
        super();
    }

    public Day7(List<String> input) {
        super(input);
    }

    public Day7 testMode() {
        basicTime = 0;
        workers = 2;
        return this;
    }

    record Tasks(Map<String,List<String>> taskPrecedence) {
        public List<String> getFirstTasks() {
            List<String> predecessors = taskPrecedence.values().stream()
                    .flatMap(List::stream)
                    .distinct()
                    .toList();
            List<String> tasks = taskPrecedence.keySet().stream()
                    .distinct()
                    .toList();

            return predecessors.stream()
                    .filter(t -> !tasks.contains(t))
                    .toList();
        }

        public List<String> getAllTasks() {
            return Stream.concat(
                    taskPrecedence.keySet().stream(),
                    taskPrecedence.values().stream().flatMap(List::stream)
            )
                    .distinct()
                    .toList();
        }

        public List<String> next(List<String> doneTasks) {
            return taskPrecedence.entrySet().stream()
                    .filter(t -> new HashSet<>(doneTasks).containsAll(t.getValue()))
                    .map(Map.Entry::getKey)
                    .toList();
        }
    }


    @Override
    public String firstStar() {
        Tasks tasks = this.getInput(Day7::parseInstructions);

        List<String> inProgress = tasks.getFirstTasks().stream()
                        .sorted(String::compareTo)
                        .collect(Collectors.toList());
        List<String> done = new ArrayList<>();

        while (!inProgress.isEmpty()) {
            done.add(inProgress.remove(0));

            inProgress = Stream.concat(inProgress.stream(), tasks.next(done).stream())
                    .distinct()
                    .filter(t -> !done.contains(t))
                    .sorted(String::compareTo)
                    .collect(Collectors.toList());
        }

        return String.join("", done);
    }

    @Override
    public Integer secondStar() {
        Tasks tasks = this.getInput(Day7::parseInstructions);

        Map<String,Integer> taskDuration = tasks.getAllTasks().stream()
                .collect(Collectors.toMap(t -> t, t -> basicTime + TASKS.indexOf(t) + 1));

        List<String> waiting = tasks.getFirstTasks().stream()
                .sorted(String::compareTo)
                .collect(Collectors.toList());
        List<String> inProgress = new ArrayList<>();
        List<String> done = new ArrayList<>();

        int time = 0;
        while (!waiting.isEmpty() || !inProgress.isEmpty()) {
            while (inProgress.size() < workers && !waiting.isEmpty()) {
                inProgress.add(waiting.remove(0));
            }

            boolean doneTasks = false;
            for (int i = 0; i < inProgress.size(); i++) {
                String current = inProgress.get(i);
                taskDuration.put(current, taskDuration.get(current) - 1);
                if (taskDuration.get(current) == 0) {
                    done.add(inProgress.remove(i));
                    doneTasks = true;
                    i--;
                }
            }

            if (doneTasks) {
                waiting = Stream.concat(waiting.stream(), tasks.next(done).stream())
                        .distinct()
                        .filter(t -> !done.contains(t) && !inProgress.contains(t))
                        .sorted(String::compareTo)
                        .collect(Collectors.toList());
            }

            time++;
        }

        return time;
    }

    private static Tasks parseInstructions(List<String> input) {
        Pattern instructionPattern = Pattern.compile(".+([A-Z]).+([A-Z]).+");

        Map<String,List<String>> taskPrecedence = input.stream()
                .map(l -> {
                    Matcher m = instructionPattern.matcher(l);
                    if (!m.find()) throw new RuntimeException("Regex not matching for : " + l);
                    return m;
                })
                .map(m -> new AbstractMap.SimpleEntry<>(
                        m.group(2),
                        m.group(1)
                ))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.toList()
                        )
                ));
        return new Tasks(taskPrecedence);
    }
}
