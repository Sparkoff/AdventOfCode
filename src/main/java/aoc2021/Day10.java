package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Day10 extends DayBase<List<String>, Long> {

    public Day10() {
        super();
    }

    public Day10(List<String> input) {
        super(input);
    }

    enum SyntaxErrorType {
        ILLEGAL, MISSING
    }

    record SyntaxError(SyntaxErrorType type, String seq) {}


    @Override
    public Long firstStar() {
        List<String> subsystems = this.getInput(PuzzleInput::asStringList);

        return subsystems.stream()
                .map(Day10::checkSyntax)
                .filter(s -> s != null && s.type == SyntaxErrorType.ILLEGAL)
                .mapToLong(Day10::syntaxErrorScore)
                .sum();
    }

    @Override
    public Long secondStar() {
        List<String> subsystems = this.getInput(PuzzleInput::asStringList);

        List<Long> missingScores = subsystems.stream()
                .map(Day10::checkSyntax)
                .filter(s -> s != null && s.type == SyntaxErrorType.MISSING)
                .map(Day10::syntaxErrorScore)
                .sorted(Comparator.naturalOrder())
                .toList();
        return missingScores.get(missingScores.size() / 2);
    }

    private static SyntaxError checkSyntax(String line) {
        List<String> chunks = new ArrayList<>();
        for (String c : line.split("")) {
            if (")]}>".contains(c)) {
                String chunk = chunks.remove(chunks.size() - 1) + c;
                if (!"()[]{}<>".contains(chunk)) {
                    return new SyntaxError(SyntaxErrorType.ILLEGAL, c);
                }
            } else {
                chunks.add(c);
            }
        }
        if (chunks.size() > 0) {
            Collections.reverse(chunks);
            String missing = chunks.stream()
                    .map(c -> switch (c) {
                        case "(" -> ")";
                        case "[" -> "]";
                        case "{" -> "}";
                        case "<" -> ">";
                        default -> "";
                    })
                    .collect(Collectors.joining());
            return new SyntaxError(SyntaxErrorType.MISSING, missing);
        } else {
            return null;
        }
    }

    private static long syntaxErrorScore(SyntaxError syntaxError) {
        if (syntaxError.type == SyntaxErrorType.ILLEGAL) {
            return switch (syntaxError.seq) {
                case ")" -> 3L;
                case "]" -> 57L;
                case "}" -> 1197L;
                case ">" -> 25137L;
                default -> 0L;
            };
        } else {
            return Arrays.stream(syntaxError.seq.split(""))
                    .map(c -> switch (c) {
                        case ")" -> 1L;
                        case "]" -> 2L;
                        case "}" -> 3L;
                        case ">" -> 4L;
                        default -> 0L;
                    })
                    .reduce(0L, (score, p) -> score * 5L + p);
        }
    }
}