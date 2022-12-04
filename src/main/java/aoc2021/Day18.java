package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class Day18 extends DayBase<List<String>, Integer> {

    public Day18() {
        super();
    }

    public Day18(List<String> input) {
        super(input);
    }

    static class Pair {
        private Integer value = null;
        private Pair left = null;
        private Pair right = null;
        private Pair parent;
        private int level;

        enum Side {
            LEFT, RIGHT, UP, DOWN;

            public Side opposite() {
                switch (this) {
                    case LEFT -> {  return RIGHT; }
                    case RIGHT -> { return LEFT; }
                }
                return null;
            }
        }

        public Pair() {
            this.parent = null;
            this.level = 1;
        }

        public Pair(Pair parent) {
            this.parent = parent;
            this.level = parent.level + 1;
        }

        public void setValue(int value) {
            this.value = value;
            clean();
        }

        public void setPair(Pair pair) {
            if (this.left == null) {
                this.left = pair;
            } else {
                this.right = pair;
            }
            this.value = null;
        }

        public void reduce() {
            boolean actionOperated;

            do {
                // search for explode from left to right
                // always do explode before any split
                actionOperated = this.left.checkExplode();
                if (actionOperated) {
                    continue;
                }
                actionOperated = this.right.checkExplode();
                if (actionOperated) {
                    continue;
                }

                // then search for split from left to right if no explode found
                actionOperated = this.left.checkSplit();
                if (actionOperated) {
                    continue;
                }
                actionOperated = this.right.checkSplit();
            } while (actionOperated);
        }

        private boolean checkExplode() {
            if (this.value != null) return false;

            if (this.left.isPurePair() && this.left.level > 4) {
                explode(Side.LEFT);
                return true;
            } else {
                boolean hasExploded = this.left.checkExplode();
                if (hasExploded) {
                    return true;
                }
            }

            if (this.right.isPurePair() && this.right.level > 4) {
                explode(Side.RIGHT);
                return true;
            } else {
                return this.right.checkExplode();
            }
        }

        private boolean checkSplit() {
            if (this.value != null) return false;

            if (this.left.value != null) {
                if (this.left.value >= 10) {
                    split(Side.LEFT);
                    return true;
                }
            } else {
                boolean hasSplit = this.left.checkSplit();
                if (hasSplit) {
                    return true;
                }
            }

            if (this.right.value != null) {
                if (this.right.value >= 10) {
                    split(Side.RIGHT);
                    return true;
                }
            } else {
                return this.right.checkSplit();
            }

            return false;
        }

        private void explode(Side roll) {
            Pair pair = roll == Side.LEFT ? this.left : this.right;

            pair.updateClosestValue(Side.DOWN, Side.LEFT, pair.left.value);
            pair.updateClosestValue(Side.DOWN, Side.RIGHT, pair.right.value);

            pair.setValue(0);
        }

        private void updateClosestValue(Side pitch, Side roll, int value) {
            if (this.parent == null) return;

            if (pitch == Side.DOWN) {
                Pair pair = roll == Side.LEFT ? this.parent.left : this.parent.right;

                if (pair.value != null) {
                    if (roll == Side.LEFT) {
                        this.parent.left.value += value;
                    } else {
                        this.parent.right.value += value;
                    }
                } else if (pair == this) {
                    this.parent.updateClosestValue(Side.DOWN, roll, value);
                } else {
                    pair.updateClosestValue(Side.UP, roll.opposite(), value);
                }
            } else {
                Pair pair = roll == Side.LEFT ? this.left : this.right;

                if (pair.value != null) {
                    if (roll == Side.LEFT) {
                        this.left.value += value;
                    } else {
                        this.right.value += value;
                    }
                } else {
                    pair.updateClosestValue(Side.UP, roll, value);
                }
            }

        }

        private void split(Side roll) {
            Pair pair = roll == Side.LEFT ? this.left : this.right;

            int value = pair.value;

            pair.deepClean();
            pair = new Pair(this);
            pair.setPair(new Pair(pair));
            pair.left.setValue((int) Math.floor(value / 2.));
            pair.setPair(new Pair(pair));
            pair.right.setValue((int) Math.ceil(value / 2.));

            if (roll == Side.LEFT) {
                this.left = pair;
            } else {
                this.right = pair;
            }
        }

        public static Pair sum(Pair left, Pair right) {
            left.increaseLevel();
            right.increaseLevel();
            Pair root = new Pair();
            root.left = left;
            root.left.parent = root;
            root.right = right;
            root.right.parent = root;
            root.reduce();
            return root;
        }

        public static Pair sumAll(List<Pair> pairs) {
            Pair first = pairs.remove(0);
            return pairs.stream()
                    .reduce(first, Pair::sum);
        }

        public int computeMagnitude() {
            return this.value != null ?
                    this.value :
                    3 * this.left.computeMagnitude() + 2 * this.right.computeMagnitude();
        }

        private boolean isPurePair() {
            return this.left != null && this.left.value != null
                    && this.right != null && this.right.value != null;
        }

        private void increaseLevel() {
            this.level++;
            if (this.left != null) {
                this.left.increaseLevel();
            }
            if (this.right != null) {
                this.right.increaseLevel();
            }
        }

        private void clean() {
            if (this.left != null) {
                this.left.deepClean();
                this.left = null;
            }
            if (this.right != null) {
                this.right.deepClean();
                this.right = null;
            }
        }

        private void deepClean() {
            this.parent = null;
            clean();
        }

        @Override
        public String toString() {
            if (this.value != null) {
                return String.valueOf(this.value);
            } else {
                return String.format("[%s,%s]", this.left, this.right);
            }
        }

        public static Pair fromSnailfishNumber(String number) {
            Pair root = new Pair();
            Pair current = root;

            for (String s : number.substring(1).split("")) {
                if (s.equals("[")) {
                    Pair next = new Pair(current);
                    current.setPair(next);
                    current = next;
                } else if (s.equals("]")) {
                    current = current.parent;
                } else if (!s.equals(",")) {
                    Pair num = new Pair(current);
                    num.setValue(Integer.parseInt(s));
                    current.setPair(num);
                }
            }
            return root;
        }
    }

    @Override
    public Integer firstStar() {
        List<Pair> pairs = this.getInput(PuzzleInput::asStringList)
                .stream()
                .map(Pair::fromSnailfishNumber)
                .collect(Collectors.toList());
        return Pair.sumAll(pairs).computeMagnitude();
    }

    @Override
    public Integer secondStar() {
        List<String> snailfishNumbers = this.getInput(PuzzleInput::asStringList);

        return snailfishNumbers.stream()
                .flatMap(p1 -> snailfishNumbers.stream()
                        .map(p2 -> new ArrayList<>(List.of(p1, p2))))
                .filter(pair -> !pair.get(0).equals(pair.get(1)))
                .map(l -> l.stream()
                        .map(Pair::fromSnailfishNumber)
                        .collect(Collectors.toList()))
                .map(Pair::sumAll)
                .map(Pair::computeMagnitude)
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }
}


/*public class Day18 extends DayBase<List<String>, Integer> {

    public Day18() {
        super();
    }

    public Day18(List<String> input) {
        super(input);
    }

    record ExplodeResult(boolean change, Object left, Object result, Object right) {}
    record SplitResult(boolean change, Object result) {}

    @Override
    public Integer firstStar() {
        return magnitude(this.getInput(PuzzleInput::asStringList).stream()
                .map(Day18::parseSnailfishNumber)
                .reduce(Day18::add)
                .orElseThrow());
    }

    @Override
    public Integer secondStar() {
        List<String> snailfishNumbers = this.getInput(PuzzleInput::asStringList);

        return snailfishNumbers.stream()
                .flatMap(p1 -> snailfishNumbers.stream()
                        .map(p2 -> new ArrayList<>(List.of(p1, p2))))
                .filter(pair -> !pair.get(0).equals(pair.get(1)))
                .map(l -> magnitude(l.stream()
                        .map(Day18::parseSnailfishNumber)
                        .reduce(Day18::add)
                        .orElseThrow()))
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private static Object addLeft(Object x, Integer n) {
        if (n == null) return x;
        if (x instanceof Integer) return (int) x + n;
        return List.of(addLeft(((List<?>) x).get(0), n), ((List<?>) x).get(1));
    }

    private static Object addRight(Object x, Integer n) {
        if (n == null) return x;
        if (x instanceof Integer) return (int) x + n;
        return List.of(((List<?>) x).get(0), addRight(((List<?>) x).get(1), n));
    }

    private static ExplodeResult explode(Object x, int n) {
        if (x instanceof Integer) {
            return new ExplodeResult(false, null, x, null);
        }
        if (n == 0) {
            return new ExplodeResult(true, ((List<?>) x).get(0), 0, ((List<?>) x).get(1));
        }

        Object a = ((List<?>) x).get(0);
        Object b = ((List<?>) x).get(1);

        ExplodeResult res = explode(a, n - 1);
        if (res.change) {
            return new ExplodeResult(true, res.left, List.of(res.result, addLeft(b, (Integer) res.right)), null);
        }

        res = explode(b, n - 1);
        if (res.change) {
            return new ExplodeResult(true, null, List.of(addRight(a, (Integer) res.left), res.result), res.right);
        }

        return new ExplodeResult(false, null, x, null);
    }
    private static ExplodeResult explode(List<Object> x) {
        return explode(x, 4);
    }

    private static SplitResult split(Object x) {
        if (x instanceof Integer) {
            if ((int) x >= 10) {
                return new SplitResult(true, List.of((int) Math.floor((int) x / 2.), (int) Math.ceil((int) x / 2.)));
            }
            return new SplitResult(false, x);
        }

        Object a = ((List<?>) x).get(0);
        Object b = ((List<?>) x).get(1);

        SplitResult res = split(a);
        if (res.change) {
            return new SplitResult(true, List.of(res.result, b));
        }

        res = split(b);
        return new SplitResult(res.change, List.of(a, res.result));
    }

    private static List<Object> add(List<Object> a, List<Object> b) {
        List<Object> x = List.of(a, b);
        while (true) {
            ExplodeResult explodeRes = explode(x);
            if (explodeRes.change) {
                x = (List<Object>) explodeRes.result;
                continue;
            }
            SplitResult splitRes = split(x);
            if (!splitRes.change) {
                break;
            }
            x = (List<Object>) splitRes.result;
        }
        return x;
    }

    private static int magnitude(Object item) {
        if (item instanceof Integer) return (int) item;
        return 3 * magnitude(((List<?>) item).get(0)) + 2 * magnitude(((List<?>) item).get(1));
    }

    public static List<Object> parseSnailfishNumber(String line) {
        List<Object> number = new ArrayList<>();
        List<List<Object>> stack = new ArrayList<>();
        stack.add(number);

        for (String s : line.substring(1, line.length() - 1).split("")) {
            if (s.equals("[")) {
                List<Object> next = new ArrayList<>();
                stack.add(0, next);
            } else if (s.equals("]")) {
                List<Object> current = stack.remove(0);
                stack.get(0).add(current);
            } else if (!s.equals(",")) {
                stack.get(0).add(Integer.parseInt(s));
            }
        }

        return number;
    }
}*/