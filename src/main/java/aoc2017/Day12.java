package aoc2017;

import common.DayBase;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day12 extends DayBase<Map<Integer, List<Integer>>, Integer , Integer> {

    public Day12() {
        super();
    }

    public Day12(List<String> input) {
        super(input);
    }

    @Override
    public Integer firstStar() {
        Map<Integer, List<Integer>> map = this.getInput(Day12::parseMap);

        return extractGroups(map).stream()
                .filter(g -> g.contains(0))
                .findFirst()
                .orElseThrow()
                .size();
    }

    @Override
    public Integer secondStar() {
        Map<Integer, List<Integer>> map = this.getInput(Day12::parseMap);

        return extractGroups(map).size();

    }

    private List<List<Integer>> extractGroups(Map<Integer, List<Integer>> map) {
        map = new HashMap<>(map);
        List<List<Integer>> groups = new ArrayList<>();

        while (!map.isEmpty()) {
           List<Integer> group = new ArrayList<>();
           List<Integer> toVisit = new ArrayList<>();

           toVisit.add(map.keySet().iterator().next());
           while (!toVisit.isEmpty()) {
               int node = toVisit.remove(0);
               group.add(node);

               if (map.containsKey(node)) {
                   toVisit.addAll(map.remove(node));
               }
           }

           groups.add(group.stream()
                   .distinct()
                   .toList());
        }

        return groups;
    }

    private static Map<Integer, List<Integer>> parseMap(List<String> input) {
        return input.stream()
                .map(l -> l.split(" <-> "))
                .map(l -> new AbstractMap.SimpleEntry<>(
                        Integer.parseInt(l[0]),
                        Arrays.stream(l[1].split(", "))
                                .map(Integer::parseInt)
                                .toList()
                ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
