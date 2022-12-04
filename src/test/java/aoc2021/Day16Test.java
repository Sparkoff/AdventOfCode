package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day16Test {

    @Test
    void test_first_star() {
        assertEquals(6, (new Day16(List.of("D2FE28")).firstStar()));
        assertEquals(9, (new Day16(List.of("38006F45291200")).firstStar()));
        assertEquals(14, (new Day16(List.of("EE00D40C823060")).firstStar()));
        assertEquals(16, (new Day16(List.of("8A004A801A8002F478")).firstStar()));
        assertEquals(12, (new Day16(List.of("620080001611562C8802118E34")).firstStar()));
        assertEquals(23, (new Day16(List.of("C0015000016115A2E0802F182340")).firstStar()));
        assertEquals(31, (new Day16(List.of("A0016C880162017C3686B18A3D4780")).firstStar()));
    }

    @Test
    void test_second_star() {
        assertEquals(3, (new Day16(List.of("C200B40A82")).secondStar()));
        assertEquals(54, (new Day16(List.of("04005AC33890")).secondStar()));
        assertEquals(7, (new Day16(List.of("880086C3E88112")).secondStar()));
        assertEquals(9, (new Day16(List.of("CE00C43D881120")).secondStar()));
        assertEquals(1, (new Day16(List.of("D8005AC2A8F0")).secondStar()));
        assertEquals(0, (new Day16(List.of("F600BC2D8F")).secondStar()));
        assertEquals(0, (new Day16(List.of("9C005AC2F8F0")).secondStar()));
        assertEquals(1, (new Day16(List.of("9C0141080250320F1802104A08")).secondStar()));
    }

    @Test
    void test_real_inputs() {
        Day16 day = new Day16();

        assertEquals(974, day.firstStar());
        assertEquals(180616437720L, day.secondStar());
    }

}