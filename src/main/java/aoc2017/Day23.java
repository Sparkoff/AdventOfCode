package aoc2017;

import common.DayBase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day23 extends DayBase<List<Day23.Instruction>, Integer> {

    public Day23() {
        super();
    }

    record Instruction(String type, String x, String y) {
        public void execute(Map<String, Integer> register) {
            int idxInc = 1;

            int xValue = getRegisterValue(register, x);
            int yValue = getRegisterValue(register, y);

            switch (type) {
                case "set":
                    register.put(x, yValue);
                    break;
                case "sub":
                    register.put(x, xValue - yValue);
                    break;
                case "mul":
                    register.put(x, xValue * yValue);
                    register.put("mul", register.get("mul") + 1);
                    break;
                case "jnz":
                    if (xValue != 0) {
                        idxInc = yValue;
                    }
                    break;
            }
            register.put("idx", register.get("idx") + idxInc);
        }

        private int getRegisterValue(Map<String, Integer> registers, String reg) {
            int yValue = 0;
            if (reg != null) {
                try{
                    yValue = Integer.parseInt(reg);
                } catch (NumberFormatException e) {
                    yValue = registers.get(reg);
                }
            }
            return yValue;
        }
    }

    @Override
    public Integer firstStar() {
        List<Instruction> instructions = this.getInput(Day23::parseInstructions);

        Map<String, Integer> register = new HashMap<>();
        register.put("a", 0);
        register.put("b", 0);
        register.put("c", 0);
        register.put("d", 0);
        register.put("e", 0);
        register.put("f", 0);
        register.put("g", 0);
        register.put("h", 0);
        register.put("idx", 0);
        register.put("mul", 0);

        while (register.get("idx") < instructions.size()) {
            instructions.get(register.get("idx")).execute(register);
        }

        return register.get("mul");
    }

    // set b 99
    // set c b
    //
    // jnz a 2 ------+
    //   jnz 1 5 ----|-+
    //               | |
    //   mul b 100 <-+ |
    //   sub b -100000 |
    //   set c b       |
    //   sub c -17000  |
    //                 |
    //   set f 1 <-----+------+
    //   set d 2              |
    //     set e 2 <--------+ |
    //       set g d <----+ | |
    //       mul g e      | | |
    //       sub g b      | | |
    //       jnz g 2 ---+ | | |
    //         set f 0  | | | |
    //                  | | | |
    //       sub e -1 <-+ | | |
    //       set g e      | | |
    //       sub g b      | | |
    //       jnz g -8 ----+ | |
    //     sub d -1         | |
    //     set g d          | |
    //     sub g b          | |
    //     jnz g -13 -------+ |
    //                        |
    //   jnz f 2 ----+        |
    //     sub h -1  |        |
    //               |        |
    //     set g b <-+        |
    //     sub g c            |
    //                        |
    //     jnz g 2 ------+    |
    //       jnz 1 3 --+ |    |
    //                 | |    |
    //     sub b -17 <-|-+    |
    //   jnz 1 -23 ----|------+
    //                 ^

    // b = 99
    // c = b
    // if (a != 0) {
    //   b = b * 100 + 1E5
    //   c = b + 17000
    // }
    // while (true) {
    //   f = 1
    //   d = 2
    //   while (g != 0) {
    //     e = 2
    //     while (g != 0) {
    //       g = (d * e) - b
    //       if (g == 0) {
    //         f = 0
    //       }
    //       e += 1
    //       g = e - b
    //     }
    //     d += 1
    //     g = d - b
    //   }
    //   if (f == 0) {
    //     h += 1
    //   }
    //   g = b - c
    //   if (g == 0) {
    //     break
    //   }
    //   b += 17
    // }

    // b = (a == 0) ? 99 : 109900
    // c = (a == 0) ? b : b + 17000
    //
    // while (true) {
    //   f = 1
    //   d = 2
    //   while (b != d) {
    //     e = 2
    //     while (b != e) {
    //       if (d * e == b) {
    //         f = 0
    //       }
    //       e += 1
    //     }
    //     d += 1
    //   }
    //   if (f == 0) {
    //     h += 1
    //   }
    //   if (b == c) {
    //     break
    //   }
    //   b += 17
    // }

    // b = (a == 0) ? 99 : 109900
    // c = (a == 0) ? b : b + 17000
    //
    // for (b; b <= c; b += 17) {
    //   f = 1
    //   for (d = 2; d <= b; d++) {
    //     for (e = 2; e <= b; e++) {
    //       if (d * e == b) { // search for prime number
    //         f = 0
    //       }
    //     }
    //   }
    //   if (f == 0) {
    //     h += 1  <-- f act as a boolean, h is a counter of prime numbers
    //                                   --> it counts how many numbers between b and c
    //                                       by increment of 17 that has prime numbers
    //   }
    // }

    @Override
    public Integer secondStar() {
        int c = 126900;
        int d;
        int h = 0;

        for (int b = 109900; b <= c; b += 17) {
            // optimisation : no need to search primes after sqrt(b) as multiplication
            // results would be greater than b
            for (d = 2; d <= (int) Math.sqrt(b); d++) {
                if (b % d == 0) {
                    h += 1;
                    break;
                }
            }
        }
        return h;
    }

    private static List<Instruction> parseInstructions(List<String> input) {
        return input.stream()
                .map(l -> l.split(" "))
                .map(l -> new Instruction(l[0], l[1], l[2]))
                .toList();
    }
}
