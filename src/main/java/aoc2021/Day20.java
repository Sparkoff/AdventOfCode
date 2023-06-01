package aoc2021;

import common.DayBase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class Day20 extends DayBase<Day20.Image, Integer> {

    public Day20() {
        super();
    }

    public Day20(List<String> input) {
        super(input);
    }

    record Image(String algorithm, List<String> input) {
        public int countLitPixels() {
            return input.stream()
                    .map(l -> l.split(""))
                    .flatMap(Arrays::stream)
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .sum();
        }

        public List<String> enlargeImage(boolean isEven) {
            int imageSize = input.get(0).length();
            String fillBit = isEven ? String.valueOf(algorithm.charAt(0)) : "0";

            List<String> newImage = new ArrayList<>();
            newImage.add(fillBit.repeat(imageSize));
            newImage.add(fillBit.repeat(imageSize));
            newImage.addAll(input);
            newImage.add(fillBit.repeat(imageSize));
            newImage.add(fillBit.repeat(imageSize));
            return newImage.stream()
                    .map(l -> fillBit.repeat(2) + l + fillBit.repeat(2))
                    .toList();
        }
    }

    @Override
    public Integer firstStar() {
        Image image = this.getInput(Day20::parseImageAlgorithm);

        image = applyAlgorithm(image, false);
        image = applyAlgorithm(image, true);

        return image.countLitPixels();
    }

    @Override
    public Integer secondStar() {
        Image image = this.getInput(Day20::parseImageAlgorithm);

        for (int i = 1; i <= 50; i++) {
            image = applyAlgorithm(image, i % 2 == 0);
        }

        return image.countLitPixels();
    }

    public Image applyAlgorithm(Image image, boolean isEven) {
        // add 2 layers of default bits around image to process 3x3 tiles including original borders)
        List<String> enlargedImage = image.enlargeImage(isEven);

        // final image dimension (working image have extra frame of 1 bit compared to final, which remain untouched by process)
        int xmax = enlargedImage.get(0).length() - 2;
        int ymax = enlargedImage.size() - 2;

        // enhance image
        StringBuilder newImage = new StringBuilder("0".repeat(xmax * ymax));
        for (int x = 0; x < xmax; x++) {
            for (int y = 0; y < ymax; y++) {
                String pixelBit = enlargedImage.get(y).substring(x, x + 3) +
                        enlargedImage.get(y + 1).substring(x, x + 3) +
                        enlargedImage.get(y + 2).substring(x, x + 3);
                int algoIndex = Integer.parseInt(pixelBit, 2);
                newImage.setCharAt(y * xmax + x, image.algorithm().charAt(algoIndex));
            }
        }

        return new Image(image.algorithm(), new ArrayList<>(List.of(
                newImage.toString().split("(?<=\\G.{" + xmax + "})")
        )));
    }

    private static Image parseImageAlgorithm(List<String> input) {
        List<String> bitInput = input.stream()
                .map(l -> l.replaceAll("\\.", "0").replaceAll("#", "1"))
                .collect(Collectors.toList());

        String algorithm = bitInput.remove(0);
        bitInput.remove(0);  // remove empty line
        return new Image(algorithm, bitInput);
    }
}