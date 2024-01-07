package aoc2023;

import common.DayBase;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Day7 extends DayBase<List<Day7.Hand>, Integer, Integer> {

    public Day7() {
        super();
    }

    public Day7(List<String> input) {
        super(input);
    }

    record Card(String facial) {
        public int valueRegular() {
            return switch (facial) {
                case "A" -> 14;
                case "K" -> 13;
                case "Q" -> 12;
                case "J" -> 11;
                case "T" -> 10;
                default -> Integer.parseInt(facial);
            };
        }
        public int valueJoker() {
            return facial.equals("J") ? 1 : valueRegular();
        }

        public static List<Card> fromString(String hand) {
            return Arrays.stream(hand.split(""))
                    .map(Card::new)
                    .toList();
        }
    }
    record Hand(List<Card> cards, int bid) {
        private int typeRankRegular() {
            return typeRank(cards.stream()
                    .map(Card::facial)
                    .collect(Collectors.joining("")));
        }
        private int typeRankJoker() {
            String hand = cards.stream()
                    .map(Card::facial)
                    .collect(Collectors.joining(""));

            if (!hand.contains("J") || hand.equals("JJJJJ")) {
                return typeRank(hand);
            }

            List<String> cardsInHand = cards.stream()
                    .map(Card::facial)
                    .distinct()
                    .filter(c -> !c.equals("J"))
                    .toList();
            List<String> jokerHands = List.of(hand);

            int index = hand.indexOf("J");
            while(index >= 0) {
                jokerHands = jokerHands.stream()
                        .map(h -> cardsInHand.stream()
                                .map(c -> h.replaceFirst("J", c))
                                .toList())
                        .flatMap(List::stream)
                        .toList();

                index = hand.indexOf("J", index + 1);
            }

            return jokerHands.stream()
                    .mapToInt(Hand::typeRank)
                    .max()
                    .orElseThrow();
        }
        private static int typeRank(String hand) {
            String typePattern = Arrays.stream(hand.split(""))
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .values().stream()
                    .sorted(Comparator.reverseOrder())
                    .map(String::valueOf)
                    .collect(Collectors.joining(""));
            return switch (typePattern) {
                case "5" -> 7;
                case "41" -> 6;
                case "32" -> 5;
                case "311" -> 4;
                case "221" -> 3;
                case "2111" -> 2;
                case "11111" -> 1;
                default -> throw new IllegalStateException("Unable to rank type " + typePattern);
            };
        }

        public int compareRegular(Hand other) {
            return Comparator.comparingInt(Hand::typeRankRegular)
                    .thenComparingInt(h -> h.cards.get(0).valueRegular())
                    .thenComparingInt(h -> h.cards.get(1).valueRegular())
                    .thenComparingInt(h -> h.cards.get(2).valueRegular())
                    .thenComparingInt(h -> h.cards.get(3).valueRegular())
                    .thenComparingInt(h -> h.cards.get(4).valueRegular())
                    .compare(this, other);
        }
        public int compareJoker(Hand other) {
            return Comparator.comparingInt(Hand::typeRankJoker)
                    .thenComparingInt(h -> h.cards.get(0).valueJoker())
                    .thenComparingInt(h -> h.cards.get(1).valueJoker())
                    .thenComparingInt(h -> h.cards.get(2).valueJoker())
                    .thenComparingInt(h -> h.cards.get(3).valueJoker())
                    .thenComparingInt(h -> h.cards.get(4).valueJoker())
                    .compare(this, other);
        }
    }


    @Override
    public Integer firstStar() {
        List<Hand> hands = this.getInput(Day7::parseHands);

        return totalWinnings(hands.stream()
                .sorted(Hand::compareRegular)
                .toList());
    }

    @Override
    public Integer secondStar() {
        List<Hand> hands = this.getInput(Day7::parseHands);

        return totalWinnings(hands.stream()
                .sorted(Hand::compareJoker)
                .toList());
    }

    private static int totalWinnings(List<Hand> winningHands) {
        int totalWinnings = 0;
        for (int i = 0; i < winningHands.size(); i++) {
            totalWinnings += winningHands.get(i).bid() * (i + 1);
        }
        return totalWinnings;
    }


    private static List<Hand> parseHands(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(l -> new Hand(Card.fromString(l[0]), Integer.parseInt(l[1])))
                .toList();
    }
}
