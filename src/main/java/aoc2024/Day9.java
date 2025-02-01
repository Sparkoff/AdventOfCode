package aoc2024;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;


public class Day9 extends DayBase<Day9.HardDrive, Long, Long> {

    public Day9() {
        super();
    }

    public Day9(List<String> input) {
        super(input);
    }

    enum Type { FILE, EMPTY }
    record Group(Type type, Integer id, Integer size) {}
    record HardDrive(Map<Integer, Integer> disk, List<Group> groups, int totalBlocks) {}


    @Override
    public Long firstStar() {
        HardDrive hd = this.getInput(Day9::parseDrive);

        Map<Integer, Integer> disk = new HashMap<>(hd.disk());

        // walk disk by both ends to find block swaps
        int idxUp = 0;
        int idxDown = hd.totalBlocks();
        while (true) {
            // get next empty block to fill
            while (disk.containsKey(idxUp)) {
                idxUp++;
            }
            // get next block to format
            while (!disk.containsKey(idxDown)) {
                idxDown--;
            }
            if (idxUp >= idxDown) {
                // stop when both cursor encounter
                break;
            }

            // swap to format
            disk.put(idxUp, disk.remove(idxDown));
        }

        return IntStream.range(0, disk.size())
                .mapToLong(i -> (long) i * disk.get(i))
                .sum();
    }

    @Override
    public Long secondStar() {
        HardDrive hd = this.getInput(Day9::parseDrive);

        List<Group> disk = new ArrayList<>(hd.groups());

        // walk disk by both ends to find block swaps
        int idxUp;
        int idxDown = disk.size() - 1;
        while (idxDown > 0) {
            //reset idx up to start
            idxUp = 0;

            // get next block to format
            while (disk.get(idxDown).type() == Type.EMPTY) {
                idxDown--;
            }

            // get next empty block to fill, with enough empty space
            while (disk.get(idxUp).type() == Type.FILE ||
                    disk.get(idxUp).size() < disk.get(idxDown).size()
            ) {
                idxUp++;

                if (idxUp >= idxDown) {
                    // stop when both cursor encounter
                    break;
                }
            }

            if (idxUp >= idxDown) {
                idxDown--;

                // stop when both cursor encounter
                continue;
            }

            // swap to format
            disk.add(idxUp, disk.get(idxDown));
            disk.set(idxDown + 1, new Group(Type.EMPTY, -1, disk.get(idxUp).size()));
            disk.set(idxUp + 1, new Group(Type.EMPTY, -1, disk.get(idxUp + 1).size() - disk.get(idxUp).size()));

            // clean disk empty spaces
            while (disk.get(disk.size() - 1).type() == Type.EMPTY) {
                disk.remove(disk.size() - 1);
            }
            if (idxDown > disk.size() - 1) {
                idxDown = disk.size() - 1;
            }
            for (int i = idxDown; i < disk.size() - 1; i++) {
                if (disk.get(i).type() == Type.EMPTY && disk.get(i + 1).type() == Type.EMPTY) {
                    disk.set(i, new Group(Type.EMPTY, -1, disk.get(i).size() + disk.get(i + 1).size()));
                    disk.remove(i + 1);
                    i--;
                } else if (disk.get(i).type() == Type.EMPTY && disk.get(i).size() == 0) {
                    disk.remove(i);
                    i--;
                }
            }
        }

        Map<Integer, Integer> diskMap = new HashMap<>();
        for (Group group : disk) {
            for (int i = 0; i < group.size(); i++) {
                diskMap.put(diskMap.size(), group.type() == Type.FILE ? group.id() : 0);
            }
        }

        return IntStream.range(0, diskMap.size())
                .mapToLong(i -> (long) i * diskMap.get(i))
                .sum();
    }


    private static HardDrive parseDrive(List<String> input) {
        String hd = input.get(0) + "0";

        int totalBlocks = 0;
        Map<Integer, Integer> disk = new HashMap<>();
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < hd.length() / 2; i++) {
            int file = hd.charAt(i * 2) - '0';
            int empty = hd.charAt(i * 2 + 1) - '0';

            for (int p = totalBlocks; p < totalBlocks + file; p++) {
                disk.put(p, i);
            }

            groups.add(new Group(Type.FILE, i, file));
            if (empty != 0) {
                groups.add(new Group(Type.EMPTY, -1, empty));
            }

            totalBlocks += file + empty;
        }

        return new HardDrive(disk, groups, totalBlocks);
    }
}
