package aoc2018;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day24 extends DayBase<List<Day24.Army>, Integer, Integer> {

    public Day24() {
        super();
    }

    public Day24(List<String> input) {
        super(input);
    }

    enum UnitType { IMMUNE_SYSTEM, INFECTION }
    enum AttackType { BLUDGEONING, COLD, FIRE, RADIATION, SLASHING }
    static class Army {
        private final UnitType type;
        private int count;
        private final int hp;
        private int damage;
        private final AttackType damageType;
        private final int initiative;
        private final List<AttackType> weakness;
        private final List<AttackType> immunity;

        private Army target = null;
        private boolean isTargeted = false;


        public Army(UnitType type, int count, int hp, int damage, AttackType damageType, int initiative,
                    List<AttackType> weakness, List<AttackType> immunity) {
            this.type = type;
            this.count = count;
            this.hp = hp;
            this.damage = damage;
            this.damageType = damageType;
            this.initiative = initiative;
            this.weakness = weakness;
            this.immunity = immunity;
        }

        public boolean cleanAndReset() {
            if (count == 0) return false;

            target = null;
            isTargeted = false;
            return true;
        }

        private int effectivePower() {
            return count * damage;
        }
        public boolean canTarget(Army defendingGroup) {
            return type != defendingGroup.type &&
                    !defendingGroup.isTargeted &&
                    evaluateDamagesTo(defendingGroup) > 0;
        }
        public int evaluateDamagesTo(Army target) {
            if (target.immunity.contains(damageType)) {
                return 0;
            } else if (target.weakness.contains(damageType)) {
                return effectivePower() * 2;
            } else {
                return effectivePower();
            }
        }

        public void attack() {
            if (count == 0 || target == null || target.count == 0) return;

            target.count -= evaluateDamagesTo(target) / target.hp;
            if (target.count < 0) {
                target.count = 0;
            }
        }

        public void setTarget(Army target) {
            if (target != null) {
                this.target = target;
                target.isTargeted = true;
            }
        }

        public UnitType getType() {
            return type;
        }
        public int getCount() {
            return count;
        }
        public int getInitiative() {
            return initiative;
        }

        public void boostImmune(int boost) {
            if (type == UnitType.IMMUNE_SYSTEM) {
                damage += boost;
            }
        }

        public Army copy() {
            return new Army(type, count, hp, damage, damageType, initiative, weakness, immunity);
        }
    }

    @Override
    public Integer firstStar() {
        List<Army> armies = this.getInput(Day24::parseBattleField);

        return survivorsCount(runBattle(armies.stream().map(Army::copy).toList()));
    }

    @Override
    public Integer secondStar() {
        List<Army> armies = this.getInput(Day24::parseBattleField);

        Map<Integer,Integer> battleResults = new HashMap<>();

        int minBoost = 0;
        int maxBoost = Integer.MAX_VALUE;
        int currentBoost = 1;

        while (minBoost + 1 != maxBoost) {
            if (minBoost != 0) {
                if (maxBoost == Integer.MAX_VALUE) {
                    currentBoost *= 2;
                } else {
                    currentBoost = minBoost + (maxBoost - minBoost) / 2;
                }
            }

            int finalCurrentBoost = currentBoost;
            List<Army> survivors = runBattle(armies.stream()
                    .map(Army::copy)
                    .peek(a -> a.boostImmune(finalCurrentBoost))
                    .toList());

            if (unfinishedBattle(survivors) || survivors.get(0).type == UnitType.INFECTION) {
                minBoost = currentBoost;
            } else {
                maxBoost = currentBoost;
                battleResults.put(currentBoost, survivors.stream()
                        .mapToInt(Army::getCount)
                        .sum());
            }
        }

        return battleResults.get(maxBoost);
    }

    private static List<Army> runBattle(List<Army> armies) {
        while (unfinishedBattle(armies)) {
            int fightersOnStart = survivorsCount(armies);

            List<Army> finalArmies = armies;
            armies.stream()
                    .sorted(Comparator.comparingInt(Army::effectivePower)
                            .thenComparingInt(Army::getInitiative)
                            .reversed())
                    .forEach(attackingGroup -> attackingGroup.setTarget(
                            finalArmies.stream()
                                    .filter(attackingGroup::canTarget)
                                    .max(Comparator.comparingInt(attackingGroup::evaluateDamagesTo)
                                            .thenComparingInt(Army::effectivePower)
                                            .thenComparingInt(Army::getInitiative))
                                    .orElse(null)
                    ));
            armies.stream()
                    .sorted(Comparator.comparingInt(Army::getInitiative).reversed())
                    .forEach(Army::attack);
            armies = armies.stream()
                    .filter(Army::cleanAndReset)
                    .toList();

            if (survivorsCount(armies) == fightersOnStart) {
                // no damages on both sides, tie game
                return armies;
            }
        }
        return armies;
    }

    private static int survivorsCount(List<Army> armies) {
        return armies.stream().mapToInt(Army::getCount).sum();
    }

    private static boolean unfinishedBattle(List<Army> armies) {
        return armies.stream().map(Army::getType).distinct().count() > 1;
    }


    private static List<Army> parseBattleField(List<String> input) {
        List<Army> armies = new ArrayList<>();

        Pattern armyPattern = Pattern.compile("(\\d+) units each with (\\d+) hit points (\\([a-z,;\\s]+\\) )?" +
                "with an attack that does (\\d+) ([a-z]+) damage at initiative (\\d+)");
        Pattern weakPattern = Pattern.compile("weak to ([a-z,\\s]+);?");
        Pattern immunePattern = Pattern.compile("immune to ([a-z,\\s]+);?");

        UnitType unitType = UnitType.IMMUNE_SYSTEM;
        for (String line : input) {
            if (line.equals("Immune System:")) {
                unitType = UnitType.IMMUNE_SYSTEM;
                continue;
            } else if (line.equals("Infection:")) {
                unitType = UnitType.INFECTION;
                continue;
            } else if (line.isEmpty()) {
                continue;
            }

            Matcher matcher = armyPattern.matcher(line);

            if (!matcher.find()) {
                throw new IllegalStateException("Unable to parse line: " + line);
            }

            int count = Integer.parseInt(matcher.group(1));
            int hp = Integer.parseInt(matcher.group(2));
            int damage = Integer.parseInt(matcher.group(4));
            AttackType damageType = AttackType.valueOf(matcher.group(5).toUpperCase());
            int initiative = Integer.parseInt(matcher.group(6));

            String capacity = matcher.group(3);
            List<AttackType> weakness = List.of();
            List<AttackType> immunity = List.of();

            if (capacity != null) {
                matcher = weakPattern.matcher(capacity);
                if (matcher.find()) {
                    weakness = Arrays.stream(matcher.group(1).split(", "))
                            .map(String::toUpperCase)
                            .map(AttackType::valueOf)
                            .toList();
                }

                matcher = immunePattern.matcher(capacity);
                if (matcher.find()) {
                    immunity = Arrays.stream(matcher.group(1).split(", "))
                            .map(String::toUpperCase)
                            .map(AttackType::valueOf)
                            .toList();
                }
            }

            armies.add(new Army(
                    unitType,
                    count,
                    hp,
                    damage,
                    damageType,
                    initiative,
                    weakness,
                    immunity
            ));
        }

        return armies;
    }
}
