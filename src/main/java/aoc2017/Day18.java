package aoc2017;

import common.DayBase;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;


public class Day18 extends DayBase<List<Day18.Instruction>, Integer> {

    public Day18() {
        super();
    }

    public Day18(List<String> input) {
        super(input);
    }

    enum Mode {SOUND, SEND}
    record Instruction(String type, String x, String y) {
        public Instruction(String type, String x) {
            this(type, x, null);
        }

        public static Instruction build(String[] args) {
            if (args.length == 3) {
                return new Instruction(args[0], args[1], args[2]);
            } else {
                return new Instruction(args[0], args[1]);
            }
        }

        public void execute(Map<String, Long> register, Mode mode) {
            int idxInc = 1;

            long xValue = getRegisterValue(register, x);
            long yValue = getRegisterValue(register, y);

            switch (type) {
                case "snd":
                    register.put(mode == Mode.SOUND ? "sound" : "send", xValue);
                    break;
                case "set":
                    register.put(x, yValue);
                    break;
                case "add":
                    register.put(x, xValue + yValue);
                    break;
                case "mul":
                    register.put(x, xValue * yValue);
                    break;
                case "mod":
                    register.put(x, xValue % yValue);
                    break;
                case "rcv":
                    if (mode == Mode.SOUND) {
                        if (xValue != 0) {
                            register.put("recover", register.get("sound"));
                        }
                    } else {
                        if (register.get("receive") != null) {
                            register.put(x, register.get("receive"));
                            register.put("receive", null);
                            register.put("stuck", null);
                        } else {
                            register.put("stuck", 1L);
                            return; // avoid idx increment
                        }
                    }
                    break;
                case "jgz":
                    if (xValue > 0) {
                        idxInc = (int) yValue;
                    }
                    break;
            }
            register.put("idx", register.get("idx") + idxInc);
        }

        private long getRegisterValue(Map<String, Long> registers, String reg) {
            long yValue = 0;
            if (reg != null) {
                try{
                    yValue = Long.parseLong(reg);
                } catch (NumberFormatException e) {
                    yValue = registers.get(reg);
                }
            }
            return yValue;
        }
    }

    @Override
    public Integer firstStar() {
        List<Day18.Instruction> instructions = this.getInput(Day18::parseInstructions);

        Map<String, Long> register = initRegister(instructions, Mode.SOUND);

        while (register.get("recover") == null) {
            instructions.get(Math.toIntExact(register.get("idx"))).execute(register, Mode.SOUND);
        }

        return Math.toIntExact(register.get("sound"));
    }

    @Override
    public Integer secondStar() {
        List<Day18.Instruction> instructions = this.getInput(Day18::parseInstructions);

        Map<String, Long> register0 = initRegister(instructions, Mode.SEND);
        Queue<Long> messages0 = new LinkedList<>();

        Map<String, Long> register1 = initRegister(instructions, Mode.SEND);
        register1.put("p", 1L);
        Queue<Long> messages1 = new LinkedList<>();

        int program1SendCount = 0;
        while (register0.get("stuck") == null || register1.get("stuck") == null) {
            runProgram(instructions, register0, messages0, messages1);
            program1SendCount += runProgram(instructions, register1, messages1, messages0);
        }

        return program1SendCount;
    }

    private static Map<String, Long> initRegister(List<Instruction> instructions, Mode mode) {
        Map<String, Long> register = instructions.stream()
                .map(Instruction::x)
                .distinct()
                .filter(r -> {
                    try {
                        Integer.parseInt(r);
                        return false;
                    } catch (NumberFormatException e) {
                        return true;
                    }
                })
                .collect(Collectors.toMap(r -> r, r -> 0L));

        if (mode == Mode.SOUND) {
            register.put("sound", null);
            register.put("recover", null);
        } else {
            register.put("send", null);
            register.put("receive", null);
            register.put("stuck", null);
        }
        register.put("idx", 0L);

        return register;
    }

    private static int runProgram(List<Instruction> instructions, Map<String, Long> register, Queue<Long> ownMessages, Queue<Long> otherMessages) {
        if (!ownMessages.isEmpty() && register.get("receive") == null) {
            register.put("receive", ownMessages.remove());
        }
        if (register.get("stuck") == null || register.get("receive") != null) {
            instructions.get(Math.toIntExact(register.get("idx"))).execute(register, Mode.SEND);

            if (register.get("send") != null) {
                otherMessages.add(register.get("send"));
                register.put("send", null);
                return 1;
            }
        }
        return 0;
    }

    private static List<Instruction> parseInstructions(List<String> input) {
        return input.stream()
                .map(l -> Instruction.build(l.split(" ")))
                .toList();
    }
}
