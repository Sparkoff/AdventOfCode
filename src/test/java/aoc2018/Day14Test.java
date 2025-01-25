package aoc2018;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("2018 Day14")
class Day14Test {

    @Nested
    class LinkedNodeTest {
        @Test
        void test_create_linked_node_with_single_value() {
            Day14.LinkedNode node = Day14.LinkedNode.createLinkedNodesFrom(4);
            assertEquals(4, node.getValue());
            assertEquals(4, node.getNext().getValue());
            assertEquals(4, node.getPrevious().getValue());
        }

        @Test
        void test_create_linked_node_with_multiple_values() {
            Day14.LinkedNode node = Day14.LinkedNode.createLinkedNodesFrom(12345);
            assertEquals(1, node.getValue());
            assertEquals(2, node.getNext().getValue());
            assertEquals(3, node.getNext().getNext().getValue());
            assertEquals(4, node.getNext().getNext().getNext().getValue());
            assertEquals(5, node.getNext().getNext().getNext().getNext().getValue());
            assertEquals(5, node.getPrevious().getValue());
        }

        @Test
        void test_insert_node_in_chain() {
            Day14.LinkedNode node1 = new Day14.LinkedNode(1);
            Day14.LinkedNode node2 = new Day14.LinkedNode(2);
            Day14.LinkedNode node3 = new Day14.LinkedNode(3);
            Day14.LinkedNode node4 = new Day14.LinkedNode(4);
            Day14.LinkedNode node5 = new Day14.LinkedNode(5);

            node3.insert(node4);
            node1.insert(node5);
            node1.insert(node3);
            node1.insert(node2);

            assertEquals(1, node1.getValue());
            assertEquals(2, node1.getNext().getValue());
            assertEquals(5, node1.getPrevious().getValue());

            assertEquals(2, node2.getValue());
            assertEquals(3, node2.getNext().getValue());
            assertEquals(1, node2.getPrevious().getValue());

            assertEquals(3, node3.getValue());
            assertEquals(4, node3.getNext().getValue());
            assertEquals(2, node3.getPrevious().getValue());

            assertEquals(4, node4.getValue());
            assertEquals(5, node4.getNext().getValue());
            assertEquals(3, node4.getPrevious().getValue());

            assertEquals(5, node5.getValue());
            assertEquals(1, node5.getNext().getValue());
            assertEquals(4, node5.getPrevious().getValue());
        }

        @Test
        void test_move_cursor() {
            Day14.LinkedNode current = Day14.LinkedNode.createLinkedNodesFrom(120345);

            current = current.move();
            assertEquals(0, current.getValue());

            current = current.move();
            assertEquals(3, current.getValue());

            current = current.move();
            assertEquals(2, current.getValue());

            current = current.move();
            assertEquals(4, current.getValue());
        }
    }

    @Test
    void test_first_star() {
        assertEquals("5158916779", new Day14(List.of("9")).firstStar());
        assertEquals("0124515891", new Day14(List.of("5")).firstStar());
        assertEquals("9251071085", new Day14(List.of("18")).firstStar());
        assertEquals("5941429882", new Day14(List.of("2018")).firstStar());
    }

    @Test
    void test_second_star() {
        assertEquals(9, new Day14(List.of("51589")).secondStar());
        assertEquals(5, new Day14(List.of("01245")).secondStar());
        assertEquals(18, new Day14(List.of("92510")).secondStar());
        assertEquals(2018, new Day14(List.of("59414")).secondStar());
    }

    @Test
    @Disabled("Test disabled due to long duration: 13sec")
    void test_real_inputs() {
        Day14 day = new Day14();

        assertEquals("1631191756", day.firstStar());
        assertEquals(20219475, day.secondStar());
    }
}
