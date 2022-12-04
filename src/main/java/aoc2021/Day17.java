package aoc2021;

import common.DayBase;
import common.PuzzleInput;

import java.util.Arrays;
import java.util.List;


public class Day17 extends DayBase<Day17.Target, Integer> {

    public Day17() {
        super();
    }

    public Day17(List<String> input) {
        super(input);
    }

    record Target(int xmin, int xmax, int ymin, int ymax) {
        Target(int xmin, int xmax, int ymin, int ymax) {
            this.xmin = Math.min(xmin, xmax);
            this.xmax = Math.max(xmin, xmax);
            this.ymin = Math.max(ymin, ymax);
            this.ymax = Math.min(ymin, ymax);
        }

        public boolean reached(int x, int y) {
            return reachedX(x) && reachedY(y);
        }

        public boolean reachedX(int x) {
            return x >= xmin && x <= xmax;
        }

        public boolean reachedY(int y) {
            return y <= ymin && y >= ymax;
        }
    }


    @Override
    public Integer firstStar() {
        Target target = this.getInput(Day17::parseTarget);

        // y increase until vy > 0, then decrease
        // limit reached when y fall to ymax (bottom limit of target)
        // => up -> y=0 to hmax, down -> y=hmax to 0 then ymax
        // => hmax = sum(1 .. ymax) - ymax = sum(1..ymax-1)
        return sumNaturalNumbers(Math.abs(target.ymax) - 1);
    }

    @Override
    public Integer secondStar() {
        Target target = this.getInput(Day17::parseTarget);

        int vxmax = target.xmax;  // max speed = reaching max target in one shot
        int vxmin = computeVxMin(target.xmin);  // min speed = reaching min target on last move before vx=0

        int vymin = target.ymax;  // max speed = reaching max target in one shot
        int vymax = Math.abs(target.ymax) - 1;  // learn in part 1

        int validInitialVelocity = 0;
        for (int vx = vxmin; vx <= vxmax; vx++) {
            for (int vy = vymin; vy <= vymax; vy++) {
                int t = 0, x = 0, y = 0;
                while (x < target.xmax && y > target.ymax) {
                    x = getX(vx, t);
                    y = getY(vy, t);

                    if (target.reached(x, y)) {
                        validInitialVelocity++;
                        break;
                    } else {
                        t++;
                    }
                }
            }
        }
        return validInitialVelocity;
    }

    private static int computeVxMin(int xmin) {
        for (int vi = 1; vi < xmin; vi++) {
            if (getX(vi, vi) > xmin) {
                return vi;
            }
        }
        return xmin;
    }

    /**
     * vx(T) = vx - T si vx > 0 && T < vx, then 0
     *         vx + T si vx < 0 && T < |vx|, then 0
     *         0 si vx = 0
     * x(T) = (T + 1)vx0 - sum(i=0..T)  si vx > 0 && T < vx, then vx0^2 - vx0(vx0 - 1)/2 = vx0(vx0 + 1)/2
     *        (T + 1)vx0 + sum(i=0..T)  si vx < 0 && T < |vx|
     *        0                         si vx = 0
     */
    private static Integer getX(int vx0, int t) {
        if (vx0 > 0) {
            if (t < vx0) {
                return vx0 * (t + 1) - sumNaturalNumbers(t);
            } else {
                return sumNaturalNumbers(vx0);
            }
        } else if (vx0 < 0) {
            if (t < Math.abs(vx0)) {
                return vx0 * (t + 1) + sumNaturalNumbers(t);
            } else {
                return -sumNaturalNumbers(vx0);
            }
        } else {
            return 0;
        }
    }

    /**
     * vy(T) = vy - T
     * y(T) = (T + 1)vy0 - sum(i=0..T)
     */
    private static Integer getY(int vy0, int t) {
        return vy0 * (t + 1) - sumNaturalNumbers(t);
    }

    private static Integer sumNaturalNumbers(int n) {
        return n * (n + 1) / 2;
    }

    private static Target parseTarget(List<String> input) {
        List<Integer> t = Arrays.stream(PuzzleInput.asString(input)
                        .split(": ")[1]
                        .split((", ")))
                .map(l -> l.split("=")[1])
                .map(l -> Arrays.stream(l.split("\\.\\."))
                        .map(Integer::parseInt)
                        .toList())
                .flatMap(List::stream)
                .toList();

        return new Target(t.get(0), t.get(1), t.get(2), t.get(3));
    }
}