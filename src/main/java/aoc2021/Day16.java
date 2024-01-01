package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Day16 extends DayBase<String, Long, Long> {

    public Day16() {
        super();
    }

    public Day16(List<String> input) {
        super(input);
    }

    record Packet(int version, int type, int bitSize, List<Packet> packets, long value) {
        public Packet(int version, int type, int bitSize, List<Packet> packets) {
            this(version, type, bitSize, packets, 0);
        }
        public Packet(int version, int bitSize, long value) {
            this(version, 4, bitSize, new ArrayList<>(), value);
        }

        public int sumVersions() {
            return version + packets.stream().mapToInt(Packet::sumVersions).sum();
        }

        public long getResult() {
            return switch (type) {
                case 0 -> packets.stream()
                        .mapToLong(Packet::getResult)
                        .sum();
                case 1 -> packets.stream()
                        .map(Packet::getResult)
                        .reduce(1L, (p, i) -> p * i);
                case 2 -> packets.stream()
                        .map(Packet::getResult)
                        .min(Comparator.naturalOrder())
                        .orElseThrow();
                case 3 -> packets.stream()
                        .map(Packet::getResult)
                        .max(Comparator.naturalOrder())
                        .orElseThrow();
                case 4 -> value;
                case 5 -> packets.get(0).getResult() > packets.get(1).getResult() ? 1L : 0L;
                case 6 -> packets.get(0).getResult() < packets.get(1).getResult() ? 1L : 0L;
                case 7 -> packets.get(0).getResult() == packets.get(1).getResult() ? 1L : 0L;
                default -> throw new RuntimeException("No results from type " + type);
            };
        }
    }


    @Override
    public Long firstStar() {
        return (long) parseMessage(this.getInput(Day16::parseToBinary)).sumVersions();
    }

    @Override
    public Long secondStar() {
        return parseMessage(this.getInput(Day16::parseToBinary)).getResult();
    }

    private static Packet parseMessage(String message) {
        int version = Integer.parseInt(message.substring(0, 3), 2);
        int type = Integer.parseInt(message.substring(3, 6), 2);
        message = message.substring(6);
        int bitSize = 6;

        if (type == 4) {
            StringBuilder value = new StringBuilder();
            while (true) {
                String next = message.substring(0, 5);
                message = message.substring(5);
                bitSize += 5;

                value.append(next.substring(1));

                if (next.charAt(0) == '0') {
                    return new Packet(version, bitSize, Long.parseLong(value.toString(), 2));
                }
            }
        } else {
            List<Packet> packets = new ArrayList<>();
            if (message.charAt(0) == '0') {
                int totalLength = Integer.parseInt(message.substring(1, 16), 2);
                message = message.substring(16);
                bitSize += 16;

                while (totalLength > 0) {
                    Packet p = parseMessage(message);
                    packets.add(p);
                    message = message.substring(p.bitSize);
                    bitSize += p.bitSize;
                    totalLength -= p.bitSize;
                }
                return new Packet(version, type, bitSize, packets);
            } else {
                int packetCount = Integer.parseInt(message.substring(1, 12), 2);
                message = message.substring(12);
                bitSize += 12;

                for (int i = 0; i < packetCount; i++) {
                    Packet p = parseMessage(message);
                    packets.add(p);
                    message = message.substring(p.bitSize);
                    bitSize += p.bitSize;
                }
                return new Packet(version, type, bitSize, packets);
            }
        }
    }

    private static String parseToBinary(List<String> input) {
        return Arrays.stream(PuzzleInput.asString(input).split(""))
                .map(c -> String.format("%04d", Integer.parseInt(Integer.toBinaryString(Integer.parseInt(c, 16)))))
                .collect(Collectors.joining());
    }
}