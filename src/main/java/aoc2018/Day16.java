package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Day16 extends DayBase<Day16.Program, Integer, Integer> {

    public Day16() {
        super();
    }

    public Day16(List<String> input) {
        super(input);
    }

    enum Opcode {
        ADDR, ADDI, MULR, MULI, BANR, BANI, BORR, BORI, SETR, SETI, GTIR, GTRI, GTRR, EQIR, EQRI, EQRR;

        public static List<Opcode> list() {
            return Arrays.stream(values()).collect(Collectors.toList());
        }
    }
    record OpcodeMapping(Map<Integer,Opcode> opMapping) {

        public Opcode get(int opcode) {
            return opMapping.get(opcode);
        }
    }
    record Instruction(int opcode, int input1, int input2, int output) {
        public Instruction(int[] raw) {
            this(raw[0], raw[1], raw[2], raw[3]);
        }

        public void exec(OpcodeMapping mapping, int[] registers) {
            exec(mapping.get(opcode), registers);
        }
        public void exec(Opcode op, int[] registers) {
            switch (op) {
                case ADDR -> registers[output] = registers[input1] + registers[input2];
                case ADDI -> registers[output] = registers[input1] + input2;
                case MULR -> registers[output] = registers[input1] * registers[input2];
                case MULI -> registers[output] = registers[input1] * input2;
                case BANR -> registers[output] = registers[input1] & registers[input2];
                case BANI -> registers[output] = registers[input1] & input2;
                case BORR -> registers[output] = registers[input1] | registers[input2];
                case BORI -> registers[output] = registers[input1] | input2;
                case SETR -> registers[output] = registers[input1];
                case SETI -> registers[output] = input1;
                case GTIR -> registers[output] = input1 > registers[input2] ? 1 : 0;
                case GTRI -> registers[output] = registers[input1] > input2 ? 1 : 0;
                case GTRR -> registers[output] = registers[input1] > registers[input2] ? 1 : 0;
                case EQIR -> registers[output] = input1 == registers[input2] ? 1 : 0;
                case EQRI -> registers[output] = registers[input1] == input2 ? 1 : 0;
                case EQRR -> registers[output] = registers[input1] == registers[input2] ? 1 : 0;
            }
        }
    }

    record Sample(int[] before, Instruction instruction, int[] after) {
        public Sample(int[] before, int[] rawInstruction, int[] after) {
            this(before, new Instruction(rawInstruction), after);
        }

        public Guess guess() {
            List<Opcode> matches = new ArrayList<>();
            for (Opcode opcode : Opcode.values()) {
                int[] reg = before.clone();
                instruction.exec(opcode, reg);
                if (Arrays.equals(reg, after)) {
                    matches.add(opcode);
                }
            }
            return new Guess(instruction.opcode(), matches);
        }
    }
    record Guess(int opcode, List<Opcode> matches) {}

    record Program(List<Sample> manual, List<Instruction> instructions) {}


    @Override
    public Integer firstStar() {
        Program program = this.getInput(Day16::parseProgram);

        return (int) program.manual().stream()
                .map(Sample::guess)
                .filter(g -> g.matches.size() >= 3)
                .count();
    }

    @Override
    public Integer secondStar() {
        Program program = this.getInput(Day16::parseProgram);

        Map<Integer,List<Opcode>> opGuessing = program.manual().stream()
                .map(Sample::guess)
                .collect(Collectors.groupingBy(
                        Guess::opcode,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                Day16::compactGuessedOpcodes
                        )
                ));

        OpcodeMapping opMapping = computeMapping(opGuessing);
        int[] register = new int[4];

        for (Instruction instruction : program.instructions()) {
            instruction.exec(opMapping, register);
        }

        return register[0];
    }

    private static List<Opcode> compactGuessedOpcodes(List<Guess> guesses) {
        return guesses.stream()
                .map(Guess::matches)
                .distinct()
                .reduce(Opcode.list(), (a, b) -> {
                    a.retainAll(b);
                    return a;
                });
    }

    private static OpcodeMapping computeMapping(Map<Integer, List<Opcode>> opGuessing) {
        Map<Integer,Opcode> mapping = new HashMap<>();
        while (!opGuessing.isEmpty()) {
            for (int opcodeNum : opGuessing.keySet()) {
                if (opGuessing.get(opcodeNum).size() == 1) {
                    Opcode opcode = opGuessing.get(opcodeNum).get(0);
                    mapping.put(opcodeNum, opcode);

                    opGuessing.remove(opcodeNum);
                    opGuessing.forEach((key, value) -> {
                        value.remove(opcode);
                    });
                    break;
                }
            }
        }
        return new OpcodeMapping(mapping);
    }

    private static Program parseProgram(List<String> input) {
        List<String> cleanInput = input.stream()
                .filter(l -> !l.isEmpty())
                .collect(Collectors.toList());

        List<Sample> manual = new ArrayList<>();
        while (!cleanInput.isEmpty() && cleanInput.get(0).startsWith("Before")) {
            manual.add(new Sample(
                    extractRegisterState(cleanInput.remove(0)),
                    toIntArray(cleanInput.remove(0).split(" ")),
                    extractRegisterState(cleanInput.remove(0))
            ));
        }

        List<Instruction> instructions = cleanInput.stream()
                .map(l -> Arrays.stream(l.split(" "))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .map(l -> new Instruction(l[0], l[1], l[2], l[3]))
                .toList();

        return new Program(manual, instructions);
    }
    private static int[] toIntArray(String[] array) {
        return Arrays.stream(array)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
    private static int[] extractRegisterState(String rawRegister) {
        // Before: [2, 1, 1, 0]
        // After:  [2, 0, 1, 0]
        return toIntArray(rawRegister.substring(9, rawRegister.length() - 1).split(", "));
    }
}
