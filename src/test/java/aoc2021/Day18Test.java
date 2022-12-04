package aoc2021;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class Day18Test {

    @Test
    void test_pair_explode() {
        List<Day18.Pair> pairs = Stream.of("[[[[[9,8],1],2],3],4]",
                        "[7,[6,[5,[4,[3,2]]]]]",
                        "[[6,[5,[4,[3,2]]]],1]",
                        "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]]")
                .map(Day18.Pair::fromSnailfishNumber)
                .peek(Day18.Pair::reduce)
                .toList();
        assertEquals("[[[[0,9],2],3],4]", pairs.get(0).toString());
        assertEquals("[7,[6,[5,[7,0]]]]", pairs.get(1).toString());
        assertEquals("[[6,[5,[7,0]]],3]", pairs.get(2).toString());
        assertEquals("[[3,[2,[8,0]]],[9,[5,[7,0]]]]", pairs.get(3).toString());
    }

    @Test
    void test_pair_explode_and_split() {
        Day18.Pair pair = Day18.Pair.fromSnailfishNumber("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]");
        pair.reduce();
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", pair.toString());
    }

    @Test
    void test_pair_sum_detailed() {
        List<Day18.Pair> pairs =  Stream.of("[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]",
                        "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]",
                        "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]",
                        "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]",
                        "[7,[5,[[3,8],[1,4]]]]",
                        "[[2,[2,2]],[8,[8,1]]]",
                        "[2,9]",
                        "[1,[[[9,3],9],[[9,0],[0,7]]]]",
                        "[[[5,[7,4]],7],1]",
                        "[[[[4,2],2],6],[8,7]]")
                .map(Day18.Pair::fromSnailfishNumber)
                .toList();

        Day18.Pair result = Day18.Pair.sum(pairs.get(0), pairs.get(1));
        assertEquals("[[[[4,0],[5,4]],[[7,7],[6,0]]],[[8,[7,7]],[[7,9],[5,0]]]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(2));
        assertEquals("[[[[6,7],[6,7]],[[7,7],[0,7]]],[[[8,7],[7,7]],[[8,8],[8,0]]]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(3));
        assertEquals("[[[[7,0],[7,7]],[[7,7],[7,8]]],[[[7,7],[8,8]],[[7,7],[8,7]]]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(4));
        assertEquals("[[[[7,7],[7,8]],[[9,5],[8,7]]],[[[6,8],[0,8]],[[9,9],[9,0]]]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(5));
        assertEquals("[[[[6,6],[6,6]],[[6,0],[6,7]]],[[[7,7],[8,9]],[8,[8,1]]]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(6));
        assertEquals("[[[[6,6],[7,7]],[[0,7],[7,7]]],[[[5,5],[5,6]],9]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(7));
        assertEquals("[[[[7,8],[6,7]],[[6,8],[0,8]]],[[[7,7],[5,0]],[[5,5],[5,6]]]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(8));
        assertEquals("[[[[7,7],[7,7]],[[8,7],[8,7]]],[[[7,0],[7,7]],9]]", result.toString());

        result = Day18.Pair.sum(result, pairs.get(9));
        assertEquals("[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]", result.toString());
    }

    @Test
    void test_pair_sum() {
        List<Day18.Pair> pairs = Stream.of("[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]")
                .map(Day18.Pair::fromSnailfishNumber)
                .collect(Collectors.toList());
        assertEquals("[[[[1,1],[2,2]],[3,3]],[4,4]]", Day18.Pair.sumAll(pairs).toString());

        pairs = Stream.of("[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]",
                        "[5,5]")
                .map(Day18.Pair::fromSnailfishNumber)
                .collect(Collectors.toList());
        assertEquals("[[[[3,0],[5,3]],[4,4]],[5,5]]", Day18.Pair.sumAll(pairs).toString());

        pairs = Stream.of("[1,1]",
                        "[2,2]",
                        "[3,3]",
                        "[4,4]",
                        "[5,5]",
                        "[6,6]")
                .map(Day18.Pair::fromSnailfishNumber)
                .collect(Collectors.toList());
        assertEquals("[[[[5,0],[7,4]],[5,5]],[6,6]]", Day18.Pair.sumAll(pairs).toString());

        pairs = Stream.of("[[[[4,3],4],4],[7,[[8,4],9]]]",
                        "[1,1]")
                .map(Day18.Pair::fromSnailfishNumber)
                .collect(Collectors.toList());
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", Day18.Pair.sumAll(pairs).toString());

        pairs = Stream.of("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                        "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                        "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                        "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                        "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                        "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                        "[[[[5,4],[7,7]],8],[[8,3],8]]",
                        "[[9,3],[[9,9],[6,[4,9]]]]",
                        "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                        "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]")
                .map(Day18.Pair::fromSnailfishNumber)
                .collect(Collectors.toList());
        assertEquals("[[[[6,6],[7,6]],[[7,7],[7,0]]],[[[7,7],[7,7]],[[7,8],[9,9]]]]", Day18.Pair.sumAll(pairs).toString());
    }

    @Test
    void test_pair_magnitude() {
        List<Integer> magnitude = Stream.of("[9,1]",
                        "[1,9]",
                        "[[9,1],[1,9]]",
                        "[[1,2],[[3,4],5]]",
                        "[[[[0,7],4],[[7,8],[6,0]]],[8,1]]",
                        "[[[[1,1],[2,2]],[3,3]],[4,4]]",
                        "[[[[3,0],[5,3]],[4,4]],[5,5]]",
                        "[[[[5,0],[7,4]],[5,5]],[6,6]]",
                        "[[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]")
                .map(Day18.Pair::fromSnailfishNumber)
                .map(Day18.Pair::computeMagnitude)
                .toList();
        assertEquals(List.of(29, 21, 129, 143, 1384, 445, 791, 1137, 3488), magnitude);
    }

    @Test
    void test_first_star() {
        Day18 day = new Day18(List.of("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                "[[[[5,4],[7,7]],8],[[8,3],8]]",
                "[[9,3],[[9,9],[6,[4,9]]]]",
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        ));

        assertEquals(4140, day.firstStar());
    }

    @Test
    void test_second_star() {
        Day18 day = new Day18(List.of("[[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]",
                "[[[5,[2,8]],4],[5,[[9,9],0]]]",
                "[6,[[[6,2],[5,6]],[[7,6],[4,7]]]]",
                "[[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]",
                "[[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]",
                "[[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]",
                "[[[[5,4],[7,7]],8],[[8,3],8]]",
                "[[9,3],[[9,9],[6,[4,9]]]]",
                "[[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]",
                "[[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]"
        ));

        assertEquals(3993, day.secondStar());
    }

    @Test
    void test_real_inputs() {
        Day18 day = new Day18();

        assertEquals(3574, day.firstStar());
        assertEquals(4763, day.secondStar());
    }

}