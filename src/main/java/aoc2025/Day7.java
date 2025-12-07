package aoc2025;

import common.DayBase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


public class Day7 extends DayBase<Day7.TachyonManifoldDiagram, Integer, Long> {

    public Day7() {
        super();
    }

    public Day7(List<String> input) {
        super(input);
    }
    
    
    record TachyonManifoldDiagram(int manifold, List<Integer> splitters, int width, int height) {}



    @Override
    public Integer firstStar() {
        TachyonManifoldDiagram diagram = this.getInput(Day7::parseDiagram);

        return this.countTachyonBeamSplits(diagram);
    }

    @Override
    public Long secondStar() {
        TachyonManifoldDiagram diagram = this.getInput(Day7::parseDiagram);

        return this.countQuantumTachyonParticles(diagram);
    }


    private int countTachyonBeamSplits(TachyonManifoldDiagram diagram) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        queue.add(diagram.manifold());

        int splits = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current / diagram.width() == diagram.height()) continue;

            // get down
            current += diagram.width();

            if (diagram.splitters().contains(current)) {
                if (!queue.contains(current - 1)) {
                    queue.add(current - 1);
                }
                if (!queue.contains(current + 1)) {
                    queue.add(current + 1);
                }
                splits++;
            } else if (!queue.contains(current)) {
                queue.add(current);
            }
        }
        return splits;
    }

    private long countQuantumTachyonParticles(TachyonManifoldDiagram diagram) {
        // Using a map to represent the beams in the current row of the manifold.
        // Key: position, Value: number of particles.
        Map<Integer, Long> tachyonsInCurrentRow = new HashMap<>();
        tachyonsInCurrentRow.put(diagram.manifold(), 1L);

        long totalParticles = 0L;

        while (!tachyonsInCurrentRow.isEmpty()) {
            Map<Integer, Long> tachyonsInNextRow = new HashMap<>();

            for (Map.Entry<Integer, Long> beam : tachyonsInCurrentRow.entrySet()) {
                int current = beam.getKey();
                long particles = beam.getValue();

                if (current / diagram.width() == diagram.height() - 1) {
                    totalParticles += particles;
                    continue;
                }

                // get down
                current += diagram.width();

                if (diagram.splitters().contains(current)) {
                    // The beam hits a splitter. It splits into two beams, each with the same particle count.
                    // merge particles if another beam also lands on the same spot.
                    tachyonsInNextRow.merge(current - 1, particles, Long::sum);
                    tachyonsInNextRow.merge(current + 1, particles, Long::sum);
                } else {
                    // merge particles if another beam also lands on the same spot.
                    tachyonsInNextRow.merge(current, particles, Long::sum);
                }
            }

            tachyonsInCurrentRow = tachyonsInNextRow;
        }

        return totalParticles;
    }


    private static TachyonManifoldDiagram parseDiagram(List<String> input) {
        int width = input.get(0).length();
        int height = input.size();

        int manifold = 0;
        List<Integer> splitters = new ArrayList<>();
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < input.get(y).length(); x++) {
                char c = input.get(y).charAt(x);
                if (c == 'S') {
                    manifold = y * width + x;
                } else if (c == '^') {
                    splitters.add(y * width + x);
                }
            }
        }

        return new TachyonManifoldDiagram(manifold, splitters, width, height);
    }
}
