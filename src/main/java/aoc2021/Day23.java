package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Day23 extends DayBase<Day23.State, Integer> {

    public Day23() {
        super();
    }

    public Day23(List<String> input) {
        super(input);
    }

    record State(List<List<String>> rooms, List<String> hallway) {
        public State(List<List<String>> rooms) {
            this(rooms, Collections.nCopies(11, "."));
        }

        Stream<String> amphipodsInRooms() {
            return rooms.stream().flatMap(Collection::stream).filter(a -> a.length() > 1);
        }
        Stream<String> amphipodsInHallway() {
            return hallway.stream().filter(a -> a.length() > 1);
        }
        Stream<String> amphipods() {
            return Stream.concat(amphipodsInHallway(), amphipodsInRooms());
        }

        State merge(State other) {
            List<List<String>> newRooms = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                newRooms.add(new ArrayList<>(rooms.get(i)));
                newRooms.get(i).addAll(rooms.get(i).size() / 2, other.rooms.get(i));
            }
            return new State(newRooms, hallway);
        }
    }

    record Move(State state, int energy) {}


    @Override
    public Integer firstStar() {
        State initBurrow = this.getInput(Day23::parseBurrow);

        return solve(initializeAmphipodIndexes(initBurrow));
    }

    @Override
    public Integer secondStar() {
        State initBurrow = this.getInput(Day23::parseBurrow);
        initBurrow = initBurrow.merge(parseBurrow(List.of("#D#C#B#A#", "#D#B#A#C#")));

        return solve(initializeAmphipodIndexes(initBurrow));
    }

    static State initializeAmphipodIndexes(State state) {
        List<List<String>> newRooms = new ArrayList<>();
        Map<String,Integer> amphipodIds = new HashMap<>();
        for (List<String> room : state.rooms()) {
            List<String> newRoom = new ArrayList<>();
            for (String amphipod : room) {
                int idx = amphipodIds.getOrDefault(amphipod, 1);
                newRoom.add(amphipod + idx);
                amphipodIds.put(amphipod, idx + 1);
            }
            newRooms.add(newRoom);
        }
        return new State(newRooms, state.hallway());
    }

    static int solve(State state) {
        Map<State,Integer> energyByState = new HashMap<>(Map.of(state, 0));
        Set<Move> visited = new HashSet<>();
        for (int updated = 0; energyByState.size() > updated;) {
            updated = energyByState.size();

            List<Move> possibleMoves = energyByState.entrySet().stream()
                    .filter(e -> !visited.contains(new Move(e.getKey(), e.getValue())))
                    .flatMap(e -> Logic.allPossibleMoves(e.getKey(), e.getValue()).stream())
                    .toList();

            visited.addAll(energyByState.entrySet().stream()
                    .map(e -> new Move(e.getKey(), e.getValue()))
                    .toList());

            possibleMoves.forEach(m -> energyByState.merge(m.state(), m.energy(), Math::min));
        }
        return energyByState.entrySet().stream()
                .filter(e -> Logic.finished(e.getKey()))
                .mapToInt(Map.Entry::getValue)
                .min()
                .orElse(Integer.MAX_VALUE);
    }

    static class Amphipod {
        static final Map<Character,Integer> energy = Map.of(
                'A',1,
                'B', 10,
                'C', 100,
                'D', 1000
        );
        static final List<Integer> roomToHallway = List.of(2, 4, 6, 8);

        static int computeMovingEnergy(String amphipod, int steps) {
            return energy.get(amphipod.charAt(0)) * steps;
        }
        static int getRoomLocation(String amphipod) {
            return roomToHallway.get(getRoomIndex(amphipod));
        }
        static int getRoomLocation(int roomIndex) {
            return roomToHallway.get(roomIndex);
        }
        static int getRoomIndex(String amphipod) {
            return amphipod.charAt(0) - 'A';
        }
        static boolean hasFinished(String amphipod, State state) {
            List<String> room = Burrow.getTargetRoom(amphipod, state);
            if (!room.contains(amphipod)) return false;
            return room.stream()
                    .dropWhile(a -> !a.equals(amphipod))
                    .allMatch(a -> a.startsWith(amphipod.substring(0,1)));
        }
        static boolean isInRoom(String amphipod, State state) {
            return state.amphipodsInRooms().toList().contains(amphipod);
        }
        static boolean isInHallway(String amphipod, State state) {
            return state.amphipodsInHallway().toList().contains(amphipod);
        }
    }

    static class Burrow {
        static boolean canLeaveRoom(List<String> room, String amphipod) {
            return room.stream()
                    .dropWhile(p -> p.equals("."))
                    .findFirst()
                    .orElseThrow()
                    .equals(amphipod);
        }
        static List<String> getTargetRoom(String amphipod, State state) {
            return state.rooms().get(Amphipod.getRoomIndex(amphipod));
        }
        static boolean canMoveInRoom(List<String> room, String amphipod) {
            return room.stream().allMatch(a -> a.charAt(0) == amphipod.charAt(0) || a.equals("."));
        }
        static int destinationInRoom(List<String> room) {
            return IntStream.range(0, room.size())
                    .filter(i -> !room.get(i).equals("."))
                    .findFirst()
                    .orElse(room.size()) - 1;
        }
        static boolean clearPathToRoom(String amphipod, State state) {
            return clearPath(amphipod, Amphipod.getRoomLocation(amphipod), state.hallway().indexOf(amphipod), state);
        }
        static boolean clearPath(String amphipod, int idx1, int idx2, State state) {
            List<String> path = state.hallway().subList(Math.min(idx1, idx2), Math.max(idx1, idx2) + 1);
            return path.stream().noneMatch(h -> !h.equals(amphipod) && h.length() > 1);
        }
    }

    static class Logic {
        static boolean finished(State state) {
            return IntStream.range(0, 4)
                    .mapToObj(i -> state.rooms().get(i).stream().allMatch(r -> r.charAt(0) == 'A' + i))
                    .allMatch(b -> b);
        }
        static List<Move> allPossibleMoves(State state, int energy) {
            return state.amphipods()
                    .flatMap(amph -> possibleMoves(amph, state, energy).stream())
                    .toList();
        }
        static List<Move> possibleMoves(String amphipod, State state, int energy) {
            List<Move> moves = new ArrayList<>();
            moves.addAll(roomToHallway(amphipod, state, energy));
            moves.addAll(hallwayToRoom(amphipod,  state, energy));
            return moves;
        }
        static List<Move> roomToHallway(String amphipod, State state, int energy) {
            if (Amphipod.hasFinished(amphipod, state) || !Amphipod.isInRoom(amphipod, state)) return List.of();

            int currentRoom = IntStream.range(0, 4)
                    .filter(i -> state.rooms().get(i).contains(amphipod))
                    .findAny()
                    .orElseThrow();
            List<String> room = state.rooms().get(currentRoom);
            if (!Burrow.canLeaveRoom(room, amphipod)) return List.of();

            int stepsToHallway = room.indexOf(amphipod) + 1;
            int roomLocation = Amphipod.getRoomLocation(currentRoom);

            List<Move> moves = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                if (Amphipod.roomToHallway.contains(i) || !Burrow.clearPath(amphipod, i, roomLocation, state)) continue;

                var newRoom = replace(room, room.indexOf(amphipod), ".");
                var newRooms = replace(state.rooms(), currentRoom, newRoom);
                var newHallway = replace(state.hallway(), i, amphipod);

                moves.add(new Move(
                        new State(newRooms, newHallway),
                        energy + Amphipod.computeMovingEnergy(amphipod, stepsToHallway + Math.abs(roomLocation - i))
                ));
            }
            return moves;
        }
        static List<Move> hallwayToRoom(String amphipod, State state, int energy) {
            if (!Amphipod.isInHallway(amphipod, state)) return List.of();

            List<String> targetRoom = Burrow.getTargetRoom(amphipod, state);
            int currentPosition = state.hallway().indexOf(amphipod);
            int roomLocation = Amphipod.getRoomLocation(amphipod);

            if (Burrow.canMoveInRoom(targetRoom, amphipod) && Burrow.clearPathToRoom(amphipod, state)) {
                int finalPosition = Burrow.destinationInRoom(targetRoom);

                var newRoom = replace(targetRoom, finalPosition, amphipod);
                var newRooms = replace(state.rooms(), Amphipod.getRoomIndex(amphipod), newRoom);
                var newHallway = replace(state.hallway(), currentPosition, ".");

                return List.of(new Move(
                        new State(newRooms, newHallway),
                        energy + Amphipod.computeMovingEnergy(amphipod, Math.abs(roomLocation - currentPosition) + finalPosition + 1)
                ));
            }
            return List.of();
        }
    }

    static <T> List<T> replace(List<T> list, int index, T value) {
        List<T> l = new ArrayList<>(list);
        l.set(index, value);
        return l;
    }

    private static State parseBurrow(List<String> input) {
        List<List<String>> rooms = new ArrayList<>();
        rooms.add(new ArrayList<>());
        rooms.add(new ArrayList<>());
        rooms.add(new ArrayList<>());
        rooms.add(new ArrayList<>());

        List<String> inputRooms = Arrays.stream(String.join("", input).split(""))
                .filter(s -> List.of("A", "B", "C", "D").contains(s))
                .toList();

        int roomId = 0;
        for (String amphipod : inputRooms) {
            rooms.get(roomId).add(amphipod);
            roomId = (++roomId) % 4;
        }

        return new State(rooms);
    }
}