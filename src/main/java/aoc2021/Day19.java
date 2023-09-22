package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


public class Day19 extends DayBase<List<Day19.Scanner>, Integer> {

    final List<Scanner> verifiedScanners = new ArrayList<>();

    public Day19() {
        super();
    }

    public Day19(List<String> input) {
        super(input);
    }

    record Point(int x, int y, int z) {
        public Point translate(Point vect) {
            return new Point(x + vect.x, y + vect.y, z + vect.z);
        }
        public int distance(Point other) {
            return Math.abs(x - other.x) + Math.abs(y - other.y) + Math.abs(z - other.z);
        }
        public static int product(Point p1, Point p2) {
            return p1.x * p2.x + p1.y * p2.y + p1.z * p2.z;
        }
        public Point rotate(Matrix rot) {
            return new Point(
                    product(rot.raw1(), this),
                    product(rot.raw2(), this),
                    product(rot.raw3(), this)
            );
        }
    }
    record Matrix(Point raw1, Point raw2, Point raw3) {
        public Matrix(int r11, int r12, int r13, int r21, int r22, int r23, int r31, int r32, int r33) {
            this(new Point(r11, r12, r13), new Point(r21, r22, r23), new Point(r31, r32, r33));
        }
    }
    record Transformation(Matrix rot, Point vect) {
        public Point updatePoint(Point p) {
            return p.rotate(rot).translate(vect);
        }

        public static Transformation fromRefToOther(Matrix rot, Point ref, Point other) {
            Point otherRotated = other.rotate(rot);
            Point vect = new Point(ref.x - otherRotated.x, ref.y - otherRotated.y, ref.z - otherRotated.z);
            return new Transformation(rot, vect);
        }
    }

    record Scanner(int id, Point origin, List<Point> beacons, List<Integer> distances) {
        public Scanner(int id, Point origin, List<Point> beacons) {
            this(id, origin, beacons, computeDistances(beacons));
        }

        public static List<Integer> computeDistances(List<Point> points) {
            List<Integer> distances = new ArrayList<>();
            for (int i = 0; i < points.size() - 1; i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    distances.add(points.get(i).distance(points.get(j)));
                }
            }
            return distances.stream()
                    .sorted(Comparator.naturalOrder())
                    .toList();
        }

        public List<Point> transformBeacons(Transformation t) {
            return beacons.stream()
                    .map(t::updatePoint)
                    .toList();
        }
    }

    @Override
    public Integer firstStar() {
        List<Scanner> scanners = this.getInput(Day19::parseScanners);
        processScannerVerification(scanners);

        return (int) verifiedScanners.stream()
                .map(Scanner::beacons)
                .flatMap(Collection::stream)
                .distinct()
                .count();
    }

    @Override
    public Integer secondStar() {
        List<Scanner> scanners = this.getInput(Day19::parseScanners);
        processScannerVerification(scanners);

        int maxDist = 0;
        for (int i = 0; i < verifiedScanners.size() - 1; i++) {
            for (int j = i + 1; j < verifiedScanners.size(); j++) {
                int currentDist = verifiedScanners.get(i).origin().distance(verifiedScanners.get(j).origin());
                maxDist = Math.max(maxDist, currentDist);
            }
        }

        return Scanner.computeDistances(
                verifiedScanners.stream()
                        .map(Scanner::origin)
                        .toList()
        ).stream()
                .max(Comparator.naturalOrder())
                .orElseThrow();
    }

    private void processScannerVerification(List<Scanner> scanners) {
        if (verifiedScanners.isEmpty()) {
            List<Matrix> rotations = generateRotations();
            int edgeCount = edgeCountForNNodeGraph(12);

            Scanner scanner0 = scanners.remove(0);
            verifiedScanners.add(scanner0);

            while (!scanners.isEmpty()) {
                for (int i = 0; i < scanners.size(); i++) {
                    for (Scanner ref : verifiedScanners) {
                        if (getIntersectionCount(ref.distances(), scanners.get(i).distances()) >= edgeCount) {
                            Transformation t = findTransformation(ref, scanners.get(i), rotations);

                            Scanner current = scanners.remove(i);
                            verifiedScanners.add(new Scanner(current.id, t.vect, current.transformBeacons(t), current.distances()));

                            i--;
                            break;
                        }
                    }
                }
            }
        }
    }

    private static int edgeCountForNNodeGraph(int nodeCount) {
        return nodeCount * (nodeCount - 1) / 2;  // count of indirect edges for N nodes graph
    }

    private static int getIntersectionCount(List<Integer> distanceList1, List<Integer> distanceList2) {
        int intersections = 0;
        List<Integer> availableDistances = new ArrayList<>(distanceList1);
        for (int dist : distanceList2) {
            if (availableDistances.contains(dist)) {
                intersections++;
                availableDistances.remove((Integer) dist);
            }
        }
        return intersections;
    }

    private static Transformation findTransformation(Scanner ref, Scanner current, List<Matrix> rotations) {
        for (Point beaconRef : ref.beacons) {
            for (Point beacon : current.beacons) {
                for (Matrix rot : rotations) {
                    Transformation t = Transformation.fromRefToOther(rot, beaconRef, beacon);

                    long intersection = current.transformBeacons(t).stream()
                            .filter(ref.beacons::contains)
                            .count();
                    if (intersection == 12) {
                        return t;
                    }
                }
            }
        }
        throw new RuntimeException("Transformation not found");
    }

//    private void processScannerVerification(List<Scanner> scanners) {
//        if (verifiedScanners.isEmpty()) {
//            List<Matrix> rotations = generateRotations();
//
//            Scanner scanner0 = scanners.remove(0);
//            verifiedScanners.add(scanner0);
//
//            while (scanners.size() != 0) {
//                boolean found = false;
//                for (int i = 0; i < scanners.size(); i++) {
//                    for (Scanner ref : verifiedScanners) {
//                        for (Point beaconRef : ref.beacons) {
//                            for (Point beacon : scanners.get(i).beacons) {
//                                for (Matrix rot : rotations) {
//                                    Transformation t = Transformation.fromRefToOther(rot, beaconRef, beacon);
//                                    List<Point> beaconsInRef = scanners.get(i).transformBeacons(t);
//
//                                    long intersection = beaconsInRef.stream()
//                                            .filter(ref.beacons::contains)
//                                            .count();
//                                    if (intersection == 12) {
//                                        Scanner current = scanners.remove(i);
//                                        verifiedScanners.add(new Scanner(current.id, t.vect, beaconsInRef));
//                                        found = true;
//                                        break;
//                                    }
//                                }
//                                if (found) break;
//                            }
//                            if (found) break;
//                        }
//                        if (found) break;
//                    }
//                    if (found) break;
//                }
//            }
//        }
//    }

    private static List<Scanner> parseScanners(List<String> input) {
        List<String> extendedInput = new ArrayList<>(input);
        extendedInput.add("");

        List<Scanner> scanners = new ArrayList<>();

        int id = -1;
        List<Point> beacons = new ArrayList<>();
        for (String line : extendedInput) {
            if (line.contains("scanner")) {
                id = Integer.parseInt(Arrays.stream(line.split(" ")).toList().get(2));
            } else if (line.isEmpty()) {
                scanners.add(new Scanner(id, new Point(0, 0, 0), beacons));
                id = -1;
                beacons = new ArrayList<>();
            } else {
                List<Integer> p = Arrays.stream(line.split((",")))
                        .map(Integer::parseInt)
                        .toList();
                beacons.add(new Point(p.get(0), p.get(1), p.get(2)));
            }
        }

        return scanners;
    }

    private List<Matrix> generateRotations() {
        return List.of(
                new Matrix(1,0,0,0,1,0,0,0,1),
                new Matrix(1,0,0,0,0,-1,0,1,0),
                new Matrix(1,0,0,0,0,1,0,-1,0),
                new Matrix(1,0,0,0,-1,0,0,0,-1),

                new Matrix(-1,0,0,0,0,1,0,1,0),
                new Matrix(-1,0,0,0,-1,0,0,0,1),
                new Matrix(-1,0,0,0,1,0,0,0,-1),
                new Matrix(-1,0,0,0,0,-1,0,-1,0),

                new Matrix(0,0,1,1,0,0,0,1,0),
                new Matrix(0,-1,0,1,0,0,0,0,1),
                new Matrix(0,1,0,1,0,0,0,0,-1),
                new Matrix(0,0,-1,1,0,0,0,-1,0),

                new Matrix(0,1,0,-1,0,0,0,0,1),
                new Matrix(0,0,-1,-1,0,0,0,1,0),
                new Matrix(0,0,1,-1,0,0,0,-1,0),
                new Matrix(0,-1,0,-1,0,0,0,0,-1),

                new Matrix(0,1,0,0,0,1,1,0,0),
                new Matrix(0,0,-1,0,1,0,1,0,0),
                new Matrix(0,0,1,0,-1,0,1,0,0),
                new Matrix(0,-1,0,0,0,-1,1,0,0),

                new Matrix(0,0,1,0,1,0,-1,0,0),
                new Matrix(0,-1,0,0,0,1,-1,0,0),
                new Matrix(0,1,0,0,0,-1,-1,0,0),
                new Matrix(0,0,-1,0,-1,0,-1,0,0)
        );
    }
}