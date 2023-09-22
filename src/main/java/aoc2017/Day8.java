package aoc2017;

import common.DayBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day8 extends DayBase<List<Day8.Instruction>, Integer> {

    public Day8() {
        super();
    }

    public Day8(List<String> input) {
        super(input);
    }

    enum Operator { INCREMENT, DECREMENT }
    enum Conditional {
        GREATER, GREATER_EQUAL, LOWER, LOWER_EQUAL, EQUAL, NOT_EQUAL;

        public static Conditional castFromSymbol(String symbol) {
            return switch (symbol) {
                case ">" -> GREATER;
                case ">=" -> GREATER_EQUAL;
                case "<" -> LOWER;
                case "<=" -> LOWER_EQUAL;
                case "==" -> EQUAL;
                case "!=" -> NOT_EQUAL;
                default -> throw new IllegalStateException("Unexpected value: " + symbol);
            };

        }
    }
    record Action(String register, Integer value, Operator operator) {
        public Integer perform(Integer registerValue) {
            return registerValue + (operator == Operator.INCREMENT ? value : -value);
        }

        public static Action fromString(String raw) {
            String[] parts = raw.split(" ");
            return new Action(
                    parts[0],
                    Integer.parseInt(parts[2]),
                    parts[1].equals("inc") ? Operator.INCREMENT : Operator.DECREMENT
            );
        }
    }
    record Condition(String register, Integer value, Conditional conditional) {
        public boolean isValid(int registerValue) {
            return switch (conditional) {
                case GREATER -> registerValue > value;
                case GREATER_EQUAL -> registerValue >= value;
                case LOWER -> registerValue < value;
                case LOWER_EQUAL -> registerValue <= value;
                case EQUAL -> registerValue == value;
                case NOT_EQUAL -> registerValue != value;
            };
        }
        public static Condition fromString(String raw) {
            String[] parts = raw.split(" ");
            return new Condition(
                    parts[0],
                    Integer.parseInt(parts[2]),
                    Conditional.castFromSymbol(parts[1])
            );
        }
    }
    record Instruction(Action action, Condition condition) {
        public static Instruction fromString(String raw) {
            String[] parts = raw.split(" if ");
            return new Instruction(
                    Action.fromString(parts[0]),
                    Condition.fromString(parts[1])
            );
        }
    }

    @Override
    public Integer firstStar() {
        List<Instruction> instructions = this.getInput(Day8::parseInstructions);

        Map<String, Integer> registers = runProcess(instructions);
        registers.remove("__max__");
        return Collections.max(registers.values());
    }

    @Override
    public Integer secondStar() {
        List<Instruction> instructions = this.getInput(Day8::parseInstructions);

        Map<String, Integer> registers = runProcess(instructions);
        return registers.get("__max__");
    }

    private Map<String,Integer> runProcess(List<Instruction> instructions) {
        Map<String,Integer> registers = new HashMap<>();
        registers.put("__max__", 0);

        for (Instruction instruction : instructions) {
            if (!registers.containsKey(instruction.action.register)) {
                registers.put(instruction.action.register, 0);
            }
            if (!registers.containsKey(instruction.condition.register)) {
                registers.put(instruction.condition.register, 0);
            }

            if (instruction.condition.isValid(registers.get(instruction.condition.register))) {
                registers.put(
                        instruction.action.register,
                        instruction.action.perform(registers.get(instruction.action.register))
                );

                registers.put("__max__", Collections.max(registers.values()));
            }
        }

        return registers;
    }

    private static List<Instruction> parseInstructions(List<String> input) {
        return input.stream()
                .map(Instruction::fromString)
                .toList();
    }
}
