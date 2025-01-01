package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class Day12 extends DayBase<List<Day12.ConditionRecord>, Integer, Long> {

    public Day12() {
        super();
    }

    public Day12(List<String> input) {
        super(input);
    }

    record ConditionRecord(String springs, List<Integer> pattern) {
        ConditionRecord unfold() {
            return new ConditionRecord(
                    String.join("?", Collections.nCopies(5, springs)),
                    Collections.nCopies(5, pattern).stream()
                            .flatMap(List::stream)
                            .toList()
            );
        }
    }
    
    record State(int patternIdx, int patternFindings, boolean searchingForDot) {}


    @Override
    public Integer firstStar() {
        List<ConditionRecord> records = this.getInput(Day12::parseRecords);

        return records.stream()
                .map(this::computeArrangements)
                .mapToInt(List::size)
                .sum();
    }

    @Override
    public Long secondStar() {
        List<ConditionRecord> records = this.getInput(Day12::parseRecords);

        return records.stream()
                .map(ConditionRecord::unfold)
                .mapToLong(this::computeArrangementsNFA)
                .sum();
    }

    private List<String> computeArrangements(ConditionRecord conditionRecord) {
        List<String> damagedPattern = conditionRecord.pattern().stream()
                .map(c -> "[?#]{" + c + "}")
                .toList();
        String pattern = "^[?.]*" + String.join("[?.]+", damagedPattern) + "[?.]*$";

        return exploreSprings(conditionRecord.springs(), Pattern.compile(pattern));
    }

    private List<String> exploreSprings(String current, Pattern pattern) {
        if (!current.contains("?")) {
            return List.of(current);
        }

        List<String> arrangements = new ArrayList<>();

        String next = current.replaceFirst("\\?", ".");
        if (pattern.matcher(next).find()) {
            arrangements.addAll(exploreSprings(next, pattern));
        }

        next = current.replaceFirst("\\?", "#");
        if (pattern.matcher(next).find()) {
            arrangements.addAll(exploreSprings(next, pattern));
        }

        return arrangements;
    }

    // explore using Non-Deterministic Finite Automata (NFA)
    //  https://swtch.com/~rsc/regexp/regexp1.html
    //  https://research.swtch.com/glob
    private long computeArrangementsNFA(ConditionRecord conditionRecord) {
        long stateCount = 0;
        
        Map<State, Long> currentStates = new HashMap<>();
        Map<State, Long> nextStates = new HashMap<>();

        currentStates.put(new State(0, 0, false), 1L);
        for (int springIdx = 0; springIdx < conditionRecord.springs().length(); springIdx++) {
            char currentSpring = conditionRecord.springs().charAt(springIdx);

            for (State csi : currentStates.keySet()) {
                int patternIdx = csi.patternIdx(); // pattern we are searching for
                int patternFindings = csi.patternFindings(); // count of already found # in current pattern
                boolean searchingForDot = csi.searchingForDot();

                if ((currentSpring == '#' || currentSpring == '?')
                        && patternIdx < conditionRecord.pattern().size()
                        && !searchingForDot) {

                    // we are still looking for broken springs (#)
                    if (currentSpring == '?' && patternFindings == 0) {
                        // we are not in a run of broken springs, so ? can be working
                        State s = new State(patternIdx, patternFindings, searchingForDot);
                        nextStates.put(s, nextStates.getOrDefault(s, 0L) + currentStates.get(csi));
                    }

                    patternFindings++;

                    if (patternFindings == conditionRecord.pattern().get(patternIdx)) {
                        // we've found the full next contiguous section of broken springs
                        patternIdx++;
                        patternFindings = 0;
                        searchingForDot = true; // we only want a working spring next
                    }

                    State s = new State(patternIdx, patternFindings, searchingForDot);
                    nextStates.put(s, nextStates.getOrDefault(s, 0L) + currentStates.get(csi));
                } else if ((currentSpring == '.' || currentSpring == '?')
                        && patternFindings == 0) {

                    // we are not in a contiguous run of broken springs
                    State s = new State(patternIdx, patternFindings, false);
                    nextStates.put(s, nextStates.getOrDefault(s, 0L) + currentStates.get(csi));
                }
            }

            currentStates = nextStates;
            nextStates = new HashMap<>();
        }

        // sum states that reached the end of the pattern
        for (State csi : currentStates.keySet()) {
            if (csi.patternIdx() == conditionRecord.pattern().size()) {
                stateCount += currentStates.get(csi);
            }
        }
        return stateCount;
    }


    private static List<ConditionRecord> parseRecords(List<String> input) {
        return input.stream()
                .map(row -> row.split(" "))
                .map(row -> new ConditionRecord(
                        row[0],
                        Arrays.stream(row[1].split(","))
                                .map(Integer::parseInt)
                                .toList()
                ))
                .toList();
    }
}
