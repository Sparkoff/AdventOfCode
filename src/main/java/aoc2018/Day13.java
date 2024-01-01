package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class Day13 extends DayBase<Day13.Mine, String, String> {

    public Day13() {
        super();
    }

    public Day13(List<String> input) {
        super(input);
    }

    enum Dir {
        UP, DOWN, LEFT, RIGHT;

        public static Dir fromString(char dir) {
            return switch (dir) {
                case '^' -> UP;
                case 'v' -> DOWN;
                case '<' -> LEFT;
                case '>' -> RIGHT;
                default -> throw new IllegalStateException("Unexpected value: " + dir);
            };
        }

        public Dir toLeft() {
            return switch (this) {
                case UP -> LEFT;
                case DOWN -> RIGHT;
                case LEFT -> DOWN;
                case RIGHT -> UP;
            };
        }
        public Dir toRight() {
            return switch (this) {
                case UP -> RIGHT;
                case DOWN -> LEFT;
                case LEFT -> UP;
                case RIGHT -> DOWN;
            };
        }
        public Dir rotate() {
            return switch (this) {
                case LEFT -> UP;
                case UP -> RIGHT;
                case RIGHT -> LEFT;
                default -> throw new IllegalStateException("Unexpected turn: " + this);
            };
        }
    }
    record Point(int x, int y) implements Comparable<Point> {
        public Point translate(Dir dir) {
            return switch (dir) {
                case UP -> new Point(x, y - 1);
                case DOWN -> new Point(x, y + 1);
                case LEFT -> new Point(x - 1, y);
                case RIGHT -> new Point(x + 1, y);
            };
        }

        @Override
        public int compareTo(Point other) {
            return Comparator.comparingInt(Point::y)
                    .thenComparingInt(Point::x)
                    .compare(this, other);
        }

        @Override
        public String toString() {
            return String.format("%d,%d", x, y);
        }
    }
    record Cart(int id, Point position, Dir facing, Dir nextTurn) implements Comparable<Cart> {
        public Point nextPosition() {
            return position.translate(facing);
        }

        @Override
        public int compareTo(Cart other) {
            return position.compareTo(other.position);
        }
    }
    record Mine(List<String> map, List<Cart> initialState) {
        @Override
        public List<Cart> initialState() {
            return new ArrayList<>(initialState);
        }
    }
    record Status(List<Cart> carts, List<Point> crashes) {}


    @Override
    public String firstStar() {
        Mine mine = this.getInput(Day13::parseMine);

        Status status = new Status(mine.initialState(), List.of());
        while (status.crashes().isEmpty()) {
            status = runCycle(mine.map(), status.carts());
        }

        return status.crashes.get(0).toString();
    }

    @Override
    public String secondStar() {
        Mine mine = this.getInput(Day13::parseMine);

        Status status = new Status(mine.initialState(), List.of());
        while (status.carts().size() > 1) {
            status = runCycle(mine.map(), status.carts());

        }

        return status.carts.get(0).position().toString();
    }

    private static Status runCycle(List<String> map, List<Cart> carts) {
        List<Cart> nextCarts = new ArrayList<>();
        List<Point> crashes = new ArrayList<>();

        carts.sort(Cart::compareTo);
        while (!carts.isEmpty()) {
            Cart nextCart = runCart(map, carts.remove(0));

            Optional<Cart> crash = nextCarts.stream()
                    .filter(c -> c.position().equals(nextCart.position()))
                    .findFirst();
            if (crash.isPresent()) {
                nextCarts.remove(crash.get());
                crashes.add(nextCart.position());
                continue;
            }

            crash = carts.stream()
                    .filter(c -> c.position().equals(nextCart.position()))
                    .findFirst();
            if (crash.isPresent()) {
                carts.remove(crash.get());
                crashes.add(nextCart.position());
                continue;
            }

            if (crashes.contains(nextCart.position())) {
                continue;
            }

            nextCarts.add(nextCart);
        }

        return new Status(nextCarts, crashes);
    }

    private static Cart runCart(List<String> map, Cart cart) {
        Point nextPosition = cart.nextPosition();
        char nextPathStep = map.get(nextPosition.y()).charAt(nextPosition.x());
        Dir nextFacing = cart.facing();
        Dir nextTurn = cart.nextTurn();

        switch (nextPathStep) {
            case '/':
                nextFacing = (nextFacing == Dir.UP || nextFacing == Dir.DOWN) ? nextFacing.toRight() : nextFacing.toLeft();
                break;
            case '\\':
                nextFacing = (nextFacing == Dir.UP || nextFacing == Dir.DOWN) ? nextFacing.toLeft() : nextFacing.toRight();
                break;
            case '+':
                nextFacing = switch (nextTurn) {
                    case LEFT -> nextFacing.toLeft();
                    case RIGHT -> nextFacing.toRight();
                    case UP -> nextFacing;
                    case DOWN -> throw new IllegalStateException("Unexpected direction");
                };
                nextTurn = nextTurn.rotate();
                break;
            case ' ':
                throw new IllegalStateException("Cart out of path");
            case '|':
                if (nextFacing == Dir.LEFT || nextFacing == Dir.RIGHT) {
                    throw new IllegalStateException("Cart cross the road");
                }
                break;
            case '-':
                if (nextFacing == Dir.UP || nextFacing == Dir.DOWN) {
                    throw new IllegalStateException("Cart cross the road");
                }
                break;
        }

        return new Cart(cart.id(), nextPosition, nextFacing, nextTurn);
    }

    private static Mine parseMine(List<String> input) {
        List<String> map = new ArrayList<>();
        List<Cart> carts = new ArrayList<>();

        int height = input.size();
        int width = input.get(0).length();

        for (int y = 0; y < height; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = 0; x < width; x++) {
                char current = input.get(y).charAt(x);

                if (List.of('^', 'v', '<', '>').contains(current)) {
                    carts.add(new Cart(
                            carts.size(),
                            new Point(x, y),
                            Dir.fromString(current),
                            Dir.LEFT
                    ));

                    current = List.of('^', 'v').contains(current) ? '|' : '-';
                }

                line.append(current);
            }
            map.add(line.toString());
        }

        return new Mine(map, carts);
    }
}
