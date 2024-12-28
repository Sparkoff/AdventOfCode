package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day5 extends DayBase<Day5.PrintQueue, Integer, Integer> {

    public Day5() {
        super();
    }

    public Day5(List<String> input) {
        super(input);
    }

    record PrintQueue(Map<Integer, List<Integer>> rules, List<List<Integer>> updates) {
        private List<Integer> sorted(List<Integer> pages) {
            return new ArrayList<>(pages).stream()
                    .sorted((a, b) -> {
                        if (rules.containsKey(a) && rules.get(a).contains(b)) {
                            return -1;
                        } else if (rules.containsKey(b) && rules.get(b).contains(a)) {
                            return 1;
                        }
                        return 0;
                    })
                    .toList();
        }

        public List<PageUpdate> getPageUpdate() {
            return updates.stream()
                    .map(u -> new PageUpdate(u, sorted(u)))
                    .toList();
        }
    }
    record PageUpdate(List<Integer> pages, List<Integer> ordered) {
        public boolean isOrdered() {
            return pages.equals(ordered);
        }

        public int getMiddle() {
            return ordered.get(ordered.size() / 2);
        }
    }


    @Override
    public Integer firstStar() {
        PrintQueue printQueue = this.getInput(Day5::parsePrintQueue);

        return printQueue.getPageUpdate().stream()
                .filter(PageUpdate::isOrdered)
                .mapToInt(PageUpdate::getMiddle)
                .sum();
    }

    @Override
    public Integer secondStar() {
        PrintQueue printQueue = this.getInput(Day5::parsePrintQueue);

        return printQueue.getPageUpdate().stream()
                .filter(pu -> !pu.isOrdered())
                .mapToInt(PageUpdate::getMiddle)
                .sum();
    }


    private static PrintQueue parsePrintQueue(List<String> input) {
        Map<Integer, List<Integer>> rules = new HashMap<>();
        List<List<Integer>> updates = new ArrayList<>();

        for (String line : input) {
            if (line.contains("|")) {
                List<Integer> pages = Arrays.stream(line.split("\\|"))
                        .map(Integer::parseInt)
                        .toList();
                if (!rules.containsKey(pages.get(0))) {
                    rules.put(pages.get(0), new ArrayList<>());
                }
                rules.get(pages.get(0)).add(pages.get(1));
            } else if (line.contains(",")) {
                updates.add(
                        Arrays.stream(line.split(","))
                                .map(Integer::parseInt)
                                .toList()
                );
            }
        }

        return new PrintQueue(rules, updates);
    }
}
