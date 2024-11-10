package aoc2023;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Day19 extends DayBase<Day19.Store, Integer, Long> {

    public Day19() {
        super();
    }

    public Day19(List<String> input) {
        super(input);
    }

    enum Operand { X, M, A, S }
    enum Operator { SUP, INF }
    record Condition(Operand left, Operator op, int right) {
        static Condition fromString(String cond) {
            String[] splitCond = cond.split("<");
            if (splitCond.length != 1) {
                return new Condition(
                        Operand.valueOf(splitCond[0].toUpperCase()),
                        Operator.INF,
                        Integer.parseInt(splitCond[1])
                );
            } else {
                splitCond = cond.split(">");
                return new Condition(
                        Operand.valueOf(splitCond[0].toUpperCase()),
                        Operator.SUP,
                        Integer.parseInt(splitCond[1])
                );
            }
        }

        public boolean isValid(Part part) {
            return switch (left) {
                case X -> op == Operator.SUP ? part.x() > right : part.x() < right;
                case M -> op == Operator.SUP ? part.m() > right : part.m() < right;
                case A -> op == Operator.SUP ? part.a() > right : part.a() < right;
                case S -> op == Operator.SUP ? part.s() > right : part.s() < right;
            };
        }
    }

    enum ResultType { GO, ACCEPT, REJECT }
    record Result(ResultType type, String next) {
        static Result fromString(String res) {
            if (res.equals("A")) {
                return new Result(ResultType.ACCEPT, null);
            } else if (res.equals("R")) {
                return new Result(ResultType.REJECT, null);
            } else {
                return new Result(ResultType.GO, res);
            }
        }
    }

    record Rule(Condition in, Result out) {
        public Result apply(Part part) {
            if (in == null || in.isValid(part)) {
                return out;
            }
            return null;
        }
    }
    record Workflow(List<Rule> rules) {}

    record Part(int x, int m, int a, int s) {
        public int rating() {
            return x + m + a + s;
        }

        public ResultType run(Map<String, Workflow> workflows) {
            Result r = new Result(ResultType.GO, "in");

            while (r.type() == ResultType.GO) {
                for (Rule rule : workflows.get(r.next()).rules()) {
                    Result current = rule.apply(this);
                    if (current != null) {
                        r = current;
                        break;
                    }
                }
            }

            return r.type();
        }
    }
    record Range(int min, int max) {
        Range() {
            this(1, 4000);
        }

        public Range updateMin(int newMin) {
            return new Range(newMin, max);
        }
        public Range updateMax(int newMax) {
            return new Range(min, newMax);
        }

        public long combinations() {
            return max - min + 1;
        }
    }
    record RangedPart(String workflowId, Range x, Range m, Range a, Range s) {
        RangedPart() {
            this("in", new Range(), new Range(), new Range(), new Range());
        }

        public RangedPart duplicate() {
            return this.duplicateNext(workflowId);
        }
        public RangedPart duplicateNext(String next) {
            return new RangedPart(next, x, m, a, s);
        }

        public RangedPart updateX(Range newX) {
            return new RangedPart(workflowId, newX, m, a, s);
        }
        public RangedPart updateM(Range newM) {
            return new RangedPart(workflowId, x, newM, a, s);
        }
        public RangedPart updateA(Range newA) {
            return new RangedPart(workflowId, x, m, newA, s);
        }
        public RangedPart updateS(Range newS) {
            return new RangedPart(workflowId, x, m, a, newS);
        }

        public long combinationCount() {
            return x.combinations() * m.combinations() * a.combinations() * s.combinations();
        }
    }

    record Store(Map<String, Workflow> workflows, List<Part> parts) {}


    @Override
    public Integer firstStar() {
        Store store = this.getInput(Day19::parseStore);

        return store.parts.stream()
                .filter(p -> p.run(store.workflows) == ResultType.ACCEPT)
                .mapToInt(Part::rating)
                .sum();
    }

    @Override
    public Long secondStar() {
        Store store = this.getInput(Day19::parseStore);

        return exploreWorkflows(store.workflows());
    }

    private long exploreWorkflows(Map<String, Workflow> workflows) {
        long result = 0L;

        List<RangedPart> nexts = new ArrayList<>();
        nexts.add(new RangedPart());
        while (!nexts.isEmpty()) {
            RangedPart current = nexts.remove(0);

            for (Rule rule : workflows.get(current.workflowId()).rules()) {
                if (rule.in() == null) {
                    if (rule.out().type() == ResultType.ACCEPT) {
                        result += current.combinationCount();
                    } else if (rule.out().type() == ResultType.GO) {
                        nexts.add(current.duplicateNext(rule.out().next()));
                    }
                } else {
                    Condition cond = rule.in();
                    if (cond.left() == Operand.X) {
                        if (cond.op() == Operator.SUP) {
                            // can pass
                            if (current.x().max() > cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.x().min() <= cond.right()) {
                                    pass = pass.updateX(pass.x().updateMin(cond.right() + 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.x().min() <= cond.right()) {
                                current = current.duplicate();
                                if (current.x().max() > cond.right()) {
                                    current = current.updateX(current.x().updateMax(cond.right()));
                                }
                            } else {
                                break;
                            }
                        } else {
                            // can pass
                            if (current.x().min() < cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.x().max() >= cond.right()) {
                                    pass = pass.updateX(pass.x().updateMax(cond.right() - 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.x().max() >= cond.right()) {
                                current = current.duplicate();
                                if (current.x().min() < cond.right()) {
                                    current = current.updateX(current.x().updateMin(cond.right()));
                                }
                            } else {
                                break;
                            }
                        }
                    } else if (cond.left() == Operand.M) {
                        if (cond.op() == Operator.SUP) {
                            // can pass
                            if (current.m().max() > cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.m().min() <= cond.right()) {
                                    pass = pass.updateM(pass.m().updateMin(cond.right() + 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.m().min() <= cond.right()) {
                                current = current.duplicate();
                                if (current.m().max() > cond.right()) {
                                    current = current.updateM(current.m().updateMax(cond.right()));
                                }
                            } else {
                                break;
                            }
                        } else {
                            // can pass
                            if (current.m().min() < cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.m().max() >= cond.right()) {
                                    pass = pass.updateM(pass.m().updateMax(cond.right() - 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.m().max() >= cond.right()) {
                                current = current.duplicate();
                                if (current.m().min() < cond.right()) {
                                    current = current.updateM(current.m().updateMin(cond.right()));
                                }
                            } else {
                                break;
                            }
                        }
                    } else if (cond.left() == Operand.A) {
                        if (cond.op() == Operator.SUP) {
                            // can pass
                            if (current.a().max() > cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.a().min() <= cond.right()) {
                                    pass = pass.updateA(pass.a().updateMin(cond.right() + 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.a().min() <= cond.right()) {
                                current = current.duplicate();
                                if (current.a().max() > cond.right()) {
                                    current = current.updateA(current.a().updateMax(cond.right()));
                                }
                            } else {
                                break;
                            }
                        } else {
                            // can pass
                            if (current.a().min() < cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.a().max() >= cond.right()) {
                                    pass = pass.updateA(pass.a().updateMax(cond.right() - 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.a().max() >= cond.right()) {
                                current = current.duplicate();
                                if (current.a().min() < cond.right()) {
                                    current = current.updateA(current.a().updateMin(cond.right()));
                                }
                            } else {
                                break;
                            }
                        }
                    } else if (cond.left() == Operand.S) {
                        if (cond.op() == Operator.SUP) {
                            // can pass
                            if (current.s().max() > cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.s().min() <= cond.right()) {
                                    pass = pass.updateS(pass.s().updateMin(cond.right() + 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.s().min() <= cond.right()) {
                                current = current.duplicate();
                                if (current.s().max() > cond.right()) {
                                    current = current.updateS(current.s().updateMax(cond.right()));
                                }
                            } else {
                                break;
                            }
                        } else {
                            // can pass
                            if (current.s().min() < cond.right()) {
                                RangedPart pass = current.duplicate();
                                if (pass.s().max() >= cond.right()) {
                                    pass = pass.updateS(pass.s().updateMax(cond.right() - 1));
                                }
                                if (rule.out().type() == ResultType.ACCEPT) {
                                    result += pass.combinationCount();
                                } else if (rule.out().type() == ResultType.GO) {
                                    nexts.add(pass.duplicateNext(rule.out().next()));
                                }
                            }

                            // can fail
                            if (current.s().max() >= cond.right()) {
                                current = current.duplicate();
                                if (current.s().min() < cond.right()) {
                                    current = current.updateS(current.s().updateMin(cond.right()));
                                }
                            } else {
                                break;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }


    private static Store parseStore(List<String> input) {
        int i = 0;

        Map<String, Workflow> workflows = new HashMap<>();
        for (; i < input.size(); i++) {
            if (input.get(i).isEmpty()) {
                break;
            }

            String[] line = input.get(i).split("\\{");
            String id = line[0];
            String[] rawRules = line[1].substring(0, line[1].length() - 1).split(",");

            List<Rule> rules = Arrays.stream(rawRules)
                    .map(r -> r.split(":"))
                    .map(r -> {
                        if (r.length == 1) {
                            return new Rule(null, Result.fromString(r[0]));
                        } else {
                            return new Rule(Condition.fromString(r[0]), Result.fromString(r[1]));
                        }
                    })
                    .toList();

            workflows.put(id, new Workflow(rules));
        }
        i++;

        List<Part> parts = new ArrayList<>();
        for (; i < input.size(); i++) {
            String[] line = input.get(i).substring(1, input.get(i).length() - 1).split(",");

            List<Integer> ratings = Arrays.stream(line)
                    .map(c -> c.split("=")[1])
                    .map(Integer::parseInt)
                    .toList();

            parts.add(new Part(ratings.get(0), ratings.get(1), ratings.get(2), ratings.get(3)));
        }

        return new Store(workflows, parts);
    }
}
