package aoc2023;

import common.DayBase;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Day4 extends DayBase<List<Day4.Card>, Integer, Integer> {

    public Day4() {
        super();
    }

    public Day4(List<String> input) {
        super(input);
    }

    record Card(int id, List<Integer> winningNumbers, List<Integer> draw) {
        public int score() {
            return (int) draw.stream()
                    .filter(winningNumbers::contains)
                    .count();
        }

        public List<Integer> duplicates() {
            return IntStream.rangeClosed(0, score())
                    .filter(s -> s != 0)
                    .boxed()
                    .map(s -> id + s)
                    .toList();
        }
    }


    @Override
    public Integer firstStar() {
        List<Card> cards = this.getInput(Day4::parseCard);

        return cards.stream()
                .map(Card::score)
                .mapToInt(n -> (int) Math.pow(2, n - 1))
                .sum();
    }

    @Override
    public Integer secondStar() {
        List<Card> cards = this.getInput(Day4::parseCard);

        Map<Integer,List<Integer>> cardDuplicates = cards.stream()
                .collect(Collectors.toMap(Card::id, Card::duplicates));

        Map<Integer,Integer> scratchcards = cards.stream()
                .collect(Collectors.toMap(Card::id, c -> 1));

        for (int id : scratchcards.keySet().stream().sorted().toList()) {
            for (int duplicate : cardDuplicates.get(id)) {
                scratchcards.put(duplicate, scratchcards.get(duplicate) + scratchcards.get(id));
            }
        }

        return scratchcards.values().stream()
                .mapToInt(sc -> sc)
                .sum();
    }

    private static List<Card> parseCard(List<String> input) {
        return input.stream()
                .map(line -> {
                    int id = Integer.parseInt(line.split(":")[0]
                            .replaceAll(" ", "")
                            .replace("Card", ""));
                    String[] numbers = line.split(":")[1].split("\\|");
                    List<Integer> winningNumbers = Arrays.stream(numbers[0].split(" "))
                            .filter(n -> !n.isEmpty())
                            .map(Integer::parseInt)
                            .toList();
                    List<Integer> draw = Arrays.stream(numbers[1].split(" "))
                            .filter(n -> !n.isEmpty())
                            .map(Integer::parseInt)
                            .toList();
                    return new Card(id, winningNumbers, draw);
                })
                .toList();
    }
}
