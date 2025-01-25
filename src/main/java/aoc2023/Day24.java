package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Day24 extends DayBase<List<Day24.Hailstone>, Integer, Long> {
    private long limitMin = (long) 2E14;
    private long limitMax = (long) 4E14;

    public Day24() {
        super();
    }

    public Day24(List<String> input) {
        super(input);
    }
    public Day24(List<String> input, long min, long max) {
        super(input);
        limitMin = min;
        limitMax = max;
    }

    record Pt(double x, double y, double z) {
        public Pt add(Pt other) {
            return new Pt(
                    this.x + other.x,
                    this.y + other.y,
                    this.z + other.z
            );
        }
        public Pt sub(Pt other) {
            return this.add(other.coef(-1));
        }
        public Pt coef(double c) {
            return new Pt(
                    this.x * c,
                    this.y * c,
                    this.z * c
            );
        }
        public Pt cross(Pt other) {
            return new Pt(
                    this.y * other.z - this.z * other.y,
                    this.z * other.x - this.x * other.z,
                    this.x * other.y - this.y * other.x
            );
        }
        public double dot(Pt other) {
            return this.x * other.x + this.y * other.y + this.z * other.z;
        }
        public boolean linearIndependent(Pt other) {
            Pt cross = this.cross(other);
            return cross.x() != 0 && cross.y() != 0 &&cross.z() != 0;
        }
    }
    record Plane(Pt m, double p) {
        public Plane(Hailstone h1, Hailstone h2) {
            // 3D plan: { mx = p, my = p, mz = p }
            // based on 2 (point, vect):
            //    m = (p1 - p2) ^ (v1 - v2)
            //    p = (p1 - p2) . (v1 ^ v2)
            this(
                    (h1.o().sub(h2.o())).cross(h1.v().sub(h2.v())),
                    (h1.o().sub(h2.o())).dot(h1.v().cross(h2.v()))
            );
        }
    }
    record Hailstone(Pt o, Pt v, double m, double p) {  // y = mx + p
        public Hailstone(Pt o, Pt v) {
            this(
                    o,
                    v,
                    v.y() / v.x(),
                    o.y() - (o.x() * v.y()) / v.x()
            );
        }
    }


    @Override
    public Integer firstStar() {
        List<Hailstone> hailstones = this.getInput(Day24::parseHailstones);

        int intersection = 0;
        for (int i = 0; i < hailstones.size() - 1; i++) {
            for (int j = i + 1; j < hailstones.size(); j++) {
                Hailstone h1 = hailstones.get(i);
                Hailstone h2 = hailstones.get(j);

                // check colinearity
                if (h1.m() == h2.m()) {
                    continue;
                }

                Pt pi = intersection2D(h1, h2);

                // check intersection happen in given limits
                if (pi.x() >= limitMin && pi.x() <= limitMax && pi.y() >= limitMin && pi.y() <= limitMax) {
                    // check intersection in the future : Vx and OxX have same sign
                    if (
                            h1.v().x() / Math.abs(h1.v().x()) == (pi.x() - h1.o().x()) / Math.abs(pi.x() - h1.o().x()) &&
                            h2.v().x() / Math.abs(h2.v().x()) == (pi.x() - h2.o().x()) / Math.abs(pi.x() - h2.o().x())
                    ) {
                        intersection++;
                    }
                }
            }
        }

        return intersection;
    }

    @Override
    public Long secondStar() {
        List<Hailstone> hailstones = this.getInput(Day24::parseHailstones);

        /*
        The problem is greatly over-specified (by which I mean, just the existence of any solution for rock's
        position + velocity is far from given and would be unique given just 3 independent hailstones). It's
        possible to just compute the solution directly with some (a lot) of vector math.

        Choose 3 hailstones such that their velocity vectors are linearly independent.
        Call them (p1, v1), (p2, v2), and (p3, v3).

        If the rock starts at r with velocity w, the condition that it hits hailstone i at some time t is:
        r + t*w = pi + vi*t
        r = pi + (vi-w)*t

        So another way to look at this is that we want to apply a constant adjustment "-w" to the hailstone
        velocities that make all the (pi, vi) rays go through a single common point. For rays in 3D it's a
        fairly special condition that two of them have any point in common. Also from here we'll forget about
        the "ray" part and just deal with lines (since we end up finding just one solution...it has to be the
        right one)

        For two lines (p1, v1) and (p2, v2) with (v1 x v2) != 0 (independent); a necessary and sufficient
        condition for them to have an intersection point is that (p1 - p2) . (v1 x v2) = 0. If we consider
        what values of "w" can be applied to v1 and v2 to make that happen:
        Let (p1 - p2) . (v1 x v2) = M
        (v1 - w) x (v2 - w) = (v1 x v2) - ((v1 - v2) x w)

        dot both sides with (p1 - p2). On the left we get the "adjusted" condition which we want to equal 0.
        On the right the first term becomes M:
        0 = M - (p1 - p2) . ((v1 - v2) x w)

        IOW we need to choose w s.t. (p1 - p2) . ((v1 - v2) x w) = M

        Using the definition of triple scalar product to rearrange, we get w . ((p1 - p2) x (v1 - v2)) = M
        as the condition on w. Zooming out, this equation is of form w . a = A : that is an equation for a plane.

        To narrow down w to a single point, we need three such planes, so do the same thing with (p1, p3) and
        (p2, p3) to get three equations w.a = A, w.b = B, w.c = C.

        Assuming (check if needed) that a, b, c are independent, we can just write w = p*(bxc) + q*(cxa) + r*(axb)
        as a general form, and then plug that in to the three equations above to find: A = w.a = p*a.(bxc),
        B = w.b = q*b.(cxa), C = w.c = r*c.(axb)

        It's easy to solve for p,q,r here to directly find the value of w. Here we can also make use of the
        given that w is an integer point: we'll need to divide for the first time here (by a.(bxc)) and can
        just round to the nearest integer to correct for any floating point imprecision.

        Now we know w, it is easy to apply that velocity adjustment to p1 and p2 and find where their intersection
        point is (exercise for reader or just look at the code...this is part1 but in 3D), and that's the solution
        for where the rock starts.

        https://topaz.github.io/paste/#XQAAAQCTCQAAAAAAAAA0m0pnuFI8c8h14kUamL+XYzBvHppm9lCBjoan1Q0sYYlqcLWQS0njG9969tZsWjQCla5prwqFlBf7NmX9kjiOCY7TX+bWvefIHPFw0kJtw27ueQjYL0mdn7Q6FDAoLATUFRdcl0NIZ7Ws0uhB+tljhHjpGsc1roo/acxal6l3MZPN/ALVENlwSdFKVmnz6EME/+g78MDO1aQ8PLZcU94Ji3CPqsDOgne3qQDPxCGIQSM/5ne9o/rfay+iN5g5flZBPyTc9wLxTYBt39aGtOJLshNTd0/FMJq5XtqBogaMLjwJ3Sx/DlVP5j+Q/eAqKcuJUAbYzIK2yEIutwdIWvWfAq9eZB5dH6a4Y7UUqYCgB0a1oZJzJ57mJBa7sB+hAmeGOjjDjaGdsrbt0kVL+nzHVzzuHElv8kZxp4nm/oi6nLKS+gfbt5v7tbjxI0vA6KNI7RTF0c7l/U0jGZinbC/TaPPPg/oicam2RfQ2HjlJwChFB6sMiEHJsXU3bP3rd6/Of6VfztN32WlUGSRCkPfyLZS+2XykU0fjYdd0gtU986YbX3tpWTduaOf+mgaaSt17D0VKdsxXV1AvaWDj+uCviXx4yTepZKV2/IpOlewmyC4njPHkcT/g364Zh5+UkgsA4aYPzAxGEUAxVEu2tb8eGJ6iwbHSsqXUdPa5sR82uDJxBfozM8avAcAgK/K40Ud7+zDdUXFjiDe4eq1hB9Qd13wI7lhY6s5bcxB5wtUkGsCBD3n+1xdk+ChVsAgBiasGl368HsiO8fvSO7g3B2VAiay5a9d5xNWjW2aDPkGzO9jJrnfYjluL162BwQX4q6xaaeR0NH4dNw5okgJXUJJGJhhbxPQ0jVbhtV4lRY6ewjZljRK4b+Un6yRmy6g21+8KtwbLoxwDDjzgmpmSypOP4ByIOsZJugqXnTl5g0wb1pAYWTzLAGRX7ReND+BE5uIHOCmBI/nxdEN5pH3TJHhBglmYOuSPmMPY2dKLbLz/n+B6KD/DX7FMlYuMzNZgfJqhQrRhq0mG4G28eM582M5GHGdZvmTfL3xKWtoiB8Xf5aSgSHvjHbuXL7yvU9S88o7fKy8uGNlaZlAWMbR8GzPRuEW8q6AepxqszNFpWNFq+TIpb1TkG7CgCCQ5PupBVRTKh5ap5yE8CjXWotfC7XtaKOSpRbTpHTNr9iQKICftUeSq1Lzse6YbwJ7r0t+9YPhiE6cPjRy+hstAPOZG55HdyQxRjeb8A3n1VnSo3uINnkDxYYhzYeELF3pmiVhDwSYhPJ9+FqAan+pTSEgpu4FXjprXy9aeJcXq1AeEaPpI/96yqPo=
        https://www.reddit.com/user/topaz2078/
        */

        List<Hailstone> sample = new ArrayList<>();
        sample.add(hailstones.get(0));
        int i = 1;
        while (sample.size() < 3) {
            Hailstone current = hailstones.get(i);
            if (sample.stream().map(Hailstone::v).allMatch(current.v()::linearIndependent)) {
                sample.add(current);
            }
            i++;
        }

        Plane a = new Plane(sample.get(0), sample.get(1));
        Plane b = new Plane(sample.get(0), sample.get(2));
        Plane c = new Plane(sample.get(1), sample.get(2));

        double t = a.m().dot(b.m().cross(c.m()));
        Pt w = ((b.m().cross(c.m())).coef(a.p()))
                .add((c.m().cross(a.m())).coef(b.p()))
                .add((a.m().cross(b.m())).coef(c.p()))
                .coef( 1/ t);

        Pt w1 = sample.get(0).v().sub(w);
        Pt w2 = sample.get(1).v().sub(w);
        Pt ww = w1.cross(w2);

        double e = ww.dot(sample.get(1).o().cross(w2));
        double f = ww.dot(sample.get(0).o().cross(w1));
        double g = sample.get(0).o().dot(ww);
        double s = ww.dot(ww);

        Pt r = (w1.coef(e)).add(w2.coef(-f)).add(ww.coef(g));

        return (long) ((r.x() + r.y() + r.z()) / s);
    }

    private Pt intersection2D(Hailstone h1, Hailstone h2) {
        // m1 * x + p1 == m2 * x + p2
        double x = (h2.p() - h1.p()) / (h1.m() - h2.m());
        double y = h1.m() * x + h1.p();

        return new Pt(x, y, 0);
    }


    private static List<Hailstone> parseHailstones(List<String> input) {
        return input.stream()
                .map(l -> Arrays.stream(l.split(" @ "))
                        .map(p -> Arrays.stream(p.split(", "))
                                .map(Long::parseLong)
                                .toList()
                        )
                        .map(p -> new Pt(p.get(0), p.get(1), p.get(2)))
                        .toList()
                )
                .map(l -> new Hailstone(l.get(0), l.get(1)))
                .toList();
    }
}
