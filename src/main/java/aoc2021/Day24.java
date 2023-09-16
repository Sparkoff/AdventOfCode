package aoc2021;

import common.DayBase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day24 extends DayBase<List<Day24.Instruction>, Long> {

    public Day24() {
        super();
    }

    public Day24(List<String> input) {
        super(input);
    }
    record Instruction(String operator, String a, String b) {}

    @Override
    public Long firstStar() {
        List<Instruction> procedure = this.getInput(Day24::parseMONAD);

        /*
        // z = 26 * z + w0 + 7
        // z = 26 * z + w1 + 15
        // z = 26 * z + w2 + 2
        z = 26 * (26 * (w0 + 7) + w1 + 15) + w2 + 2


        if ((z % 26) - 3 != w3) {
            z = (z / 26) * 26 + w3 + 15
        } else {
            z = z / 26                     <---- w3 = w2 + 2 - 3 = w2 - 1
        }
        // z = 26 * (w0 + 7) + w1 + 15

        // z = 26 * z + w4 + 14
        z = 26 * (26 * (w0 + 7) + w1 + 15) + w4 + 14

        if ((z % 26) - 9 != w5) {
            z = (z / 26) * 26 + w5 + 2
        } else {
            z = z / 26                     <---- w5 = w4 + 14 - 9 = w4 + 5
        }
        // z = 26 * (w0 + 7) + w1 + 15

        // z = 26 * z + w6 + 15
        z = 26 * (26 * (w0 + 7) + w1 + 15) + w6 + 15

        if ((z % 26) - 7 != w7) {
            z = (z / 26) * 26 + w7 + 1
        } else {
            z = z / 26                     <---- w7 = w6 + 15 - 7 = w6 + 8
        }
        // z = 26 * (w0 + 7) + w1 + 15

        if ((z % 26) - 11 != w8) {
            z = (z / 26) * 26 + w8 + 15
        } else {
            z = z / 26                     <---- w8 = w1 + 15 - 11 = w1 + 4
        }
        // z = w0 + 7

        if ((z % 26) - 4 != w9) {
            z = (z / 26) * 26 + w9 + 15
        } else {
            z = z / 26                     <---- w9 = w0 + 7 - 4 = w0 + 3
        }
        // z = 0

        // z = 26 * z + w10 + 12
        // z = 26 * z + w11 + 2
        z = 26 * (w10 + 12) + w11 + 2

        if ((z % 26) - 8 != w12) {
            z = (z / 26) * 26 + w12 + 13
        } else {
            z = z / 26                     <---- w12 = w11 + 2 - 8 = w11 - 6
        }
        // z = w10 + 12

        if ((z % 26) - 10 != w13) {
            z = (z / 26) * 26 + w13 + 13
        } else {
            z = z / 26                     <---- w13 = w10 + 12 - 10 = w10 + 2
        }
        // z = 0


        w3 = w2 - 1
        w5 = w4 + 5
        w7 = w6 + 8
        w8 = w1 + 4
        w9 = w0 + 3
        w12 = w11 - 6
        w13 = w10 + 2

        0  : 6
        1  : 5
        2  : 9
        3  : 8
        4  : 4
        5  : 9
        6  : 1
        7  : 9
        8  : 9
        9  : 9
        10 : 7
        11 : 9
        12 : 3
        13 : 9
        */

        long modelNumber = 65984919997939L;

        int check = executeRawInput(modelNumber);
        if (check != 0) return -1L;

        check = executeInterpretedInput(modelNumber);
        if (check != 0) return -2L;

        return modelNumber;
    }

    @Override
    public Long secondStar() {
        List<Instruction> procedure = this.getInput(Day24::parseMONAD);

        /*
        w3 = w2 - 1
        w5 = w4 + 5
        w7 = w6 + 8
        w8 = w1 + 4
        w9 = w0 + 3
        w12 = w11 - 6
        w13 = w10 + 2

        0  : 1
        1  : 1
        2  : 2
        3  : 1
        4  : 1
        5  : 6
        6  : 1
        7  : 9
        8  : 5
        9  : 4
        10 : 1
        11 : 7
        12 : 1
        13 : 3
        */

        long modelNumber = 11211619541713L;

        int check = executeRawInput(modelNumber);
        if (check != 0) return -1L;

        check = executeInterpretedInput(modelNumber);
        if (check != 0) return -2L;

        return modelNumber;
    }

    public int executeRawInput(long modelNumber) {
        List<Integer> input = Arrays.stream(String.valueOf(modelNumber).split(""))
                .map(Integer::parseInt)
                .toList();
        List<Instruction> procedure = this.getInput(Day24::parseMONAD);

        Map<String, Integer> vars = new HashMap<>();
        vars.put("w", 0);
        vars.put("x", 0);
        vars.put("y", 0);
        vars.put("z", 0);

        int inputIdx = 0;
        for (Instruction instruction : procedure) {
            String a = instruction.a;
            String b = instruction.b;
            int bValue = 0;

            if (b != null) {
                try {
                    bValue = Integer.parseInt(instruction.b);
                } catch (NumberFormatException ignored) {
                    bValue = vars.get(b);
                }
            }

            switch (instruction.operator) {
                case "inp" -> {
                    vars.put(a, input.get(inputIdx));
                    inputIdx++;
                }
                case "add" -> vars.put(a, vars.get(a) + bValue);
                case "mul" -> vars.put(a, vars.get(a) * bValue);
                case "div" -> vars.put(a, vars.get(a) / bValue);
                case "mod" -> vars.put(a, vars.get(a) % bValue);
                case "eql" -> vars.put(a, (vars.get(a).equals(bValue)) ? 1 : 0);
                default -> {
                }
            }

        }

        return vars.get("z");
    }

    public int executeInterpretedInput(long modelNumber) {
        List<Integer> input = Arrays.stream(String.valueOf(modelNumber).split(""))
                .map(Integer::parseInt)
                .toList();

        int z = f1(input.get(0), 0, 12, 7);
        z = f1(input.get(1), z, 11, 15);
        z = f1(input.get(2), z, 12, 2);
        z = f2(input.get(3), z, -3, 15);
        z = f1(input.get(4), z, 10, 14);
        z = f2(input.get(5), z, -9, 2);
        z = f1(input.get(6), z, 10, 15);
        z = f2(input.get(7), z, -7, 1);
        z = f2(input.get(8), z, -11, 15);
        z = f2(input.get(9), z, -4, 15);
        z = f1(input.get(10), z, 14, 12);
        z = f1(input.get(11), z, 11, 2);
        z = f2(input.get(12), z, -8, 13);
        z = f2(input.get(13), z, -10, 13);

        return z;
    }

    private int f1(int w, int z, int compare, int additional) {
        if (z % 26 + compare != w) {
            z = z * 26 + w + additional;
        }
        return z;
    }
    private int f2(int w, int z, int compare, int additional) {
        if ((z % 26) + compare != w) {
            z = (z / 26) * 26 + w + additional;
        } else {
            z = z / 26;
        }
        return z;
    }

    private static List<Instruction> parseMONAD(List<String> input) {
        return input.stream()
                .map(s -> s.split(" "))
                .map(i -> new Instruction(i[0], i[1], (i[0].equals("inp") ? null : i[2])))
                .toList();
    }

    /*
    Literal translation :
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 12
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 7
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 11
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 15
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 12
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 2
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 3
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 15
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 10
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 14
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 9
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 2
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 10
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 15
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 7
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 1
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 11
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 15
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 4
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 15
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 14
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 12
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 1
    x = x + 11
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 2
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 8
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 13
    y = y * x
    z = z + y
    w = input[]
    x = x * 0
    x = x + z
    x = x % 26
    z = z / 26
    x = x - 10
    x = (x == w) ? 1 : 0
    x = (x == 0) ? 1 : 0
    y = y * 0
    y = y + 25
    y = y * x
    y = y + 1
    z = z * y
    y = y * 0
    y = y + w
    y = y + 13
    y = y * x
    z = z + y


    Code translation:
    w = input[]
    if ((z % 26) + 12 != w) {
        z = (z * 26) + w + 7
    }

    w = input[]
    if (z % 26 + 11 != w) {
        z = (z * 26) + w + 15
    }

    w = input[]
    if (z % 26 + 12 != w) {
        z = z * 26 + w + 2
    }

    w = input[]
    if ((z % 26) - 3 != w) {
        z = (z / 26) * 26 + w + 15
    } else {
        z = z / 26
    }

    w = input[]
    if (z % 26 + 10 != w) {
        z = z * 26 + w + 14
    }

    w = input[]
    if ((z % 26) - 9 != w) {
        z = (z / 26) * 26 + w + 2
    } else {
        z = z / 26
    }

    w = input[]
    if (z % 26 + 10 != w) {
        z = z * 26 + w + 15
    }

    w = input[]
    if ((z % 26) - 7 != w) {
        z = (z / 26) * 26 + w + 1
    } else {
        z = z / 26
    }

    w = input[]
    if ((z % 26) - 11 != w) {
        z = (z / 26) * 26 + w + 15
    } else {
        z = z / 26
    }

    w = input[]
    if ((z % 26) - 4 != w) {
        z = (z / 26) * 26 + w + 15
    } else {
        z = z / 26
    }

    w = input[]
    if (z % 26 + 14 != w) {
        z = z * 26 + w + 12
    }

    w = input[]
    if (z % 26 + 11 != w) {
        z = z * 26 + w + 2
    }

    w = input[]
    if ((z % 26) - 8 != w) {
        z = (z / 26) * 26 + w + 13
    } else {
        z = z / 26
    }

    w = input[]
    if ((z % 26) - 10 != w) {
        z = (z / 26) * 26 + w + 13
    } else {
        z = z / 26
    }
     */
}