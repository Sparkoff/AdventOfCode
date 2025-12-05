package aoc2025;

import common.DayBase;

import java.util.List;


public class Day1 extends DayBase<List<Integer>, Integer, Integer> {

    public Day1() {
        super();
    }

    public Day1(List<String> input) {
        super(input);
    }

    static class Dial {
        private int position;
        private int zero = 0;

        public Dial() {
            position = 50;
        }
        
        public void move(int rotation) {
            position = Math.floorMod(position + rotation, 100);

            if (position == 0) {
                zero++;
            }
        }

        public void move0x434C49434B(int rotation) {
            // simplify rotation by evaluating total dials
            zero += Math.abs(rotation) / 100;
            int absRotation = Math.abs(rotation) % 100;

            // no final moves
            if (absRotation == 0) return;

            if (rotation < 0) {
                if (position > absRotation) {
                    position -= absRotation;
                } else if (position == absRotation) {
                    zero++;
                    position = 0;
                } else {
                    if (position != 0) {
                        zero++;
                    }
                    position += 100 - absRotation;
                }
            } else {
                if (position + absRotation < 100) {
                    position += absRotation;
                } else if (position + absRotation == 100) {
                    zero++;
                    position = 0;
                } else {
                    zero++;
                    position += absRotation - 100;
                }
            }
        }

        public int getZero() {
            return zero;
        }
    }


    @Override
    public Integer firstStar() {
        List<Integer> rotations = this.getInput(Day1::parseDocument);

        Dial dial = new Dial();
        rotations.forEach(dial::move);
        return dial.getZero();
    }

    @Override
    public Integer secondStar() {
        List<Integer> rotations = this.getInput(Day1::parseDocument);

        Dial dial = new Dial();
        rotations.forEach(dial::move0x434C49434B);
        return dial.getZero();
    }


    private static List<Integer> parseDocument(List<String> input) {
        return input.stream()
                .map(l -> {
                    int dist = Integer.parseInt(l.substring(1));
                    return l.charAt(0) == 'L' ? -dist : dist;
                })
                .toList();
    }
}
