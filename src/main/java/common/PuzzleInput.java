package common;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;


public class PuzzleInput<T> {

    private final List<String> originalInput;
    private T parsedInput;


    public PuzzleInput(String packageName, String className) {
        try {
            this.originalInput = IOUtils.readLines(
                    Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(
                            String.format("%s/%s.txt", packageName, className.toLowerCase())
                    )),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PuzzleInput(List<String> testInput) {
        this.originalInput = testInput;
    }


    public T getParsedInput(Function<List<String>, T> parser) {
        if (this.parsedInput == null) {
            this.parsedInput = parser.apply(this.originalInput);
        }
        return this.parsedInput;
    }


    public static List<String> asStringList(List<String> input) {
        return input;
    }

    public static List<Integer> asIntList(List<String> input) {
        return input.stream().map(Integer::parseInt).toList();
    }

    public static List<Integer> asInlineIntListWithComma(List<String> input) {
        return Arrays.stream(PuzzleInput.asString(input).split(","))
                .map(Integer::parseInt)
                .toList();
    }

    public static String asString(List<String> input) {
        return input.get(0);
    }

    public static Integer asInt(List<String> input) {
        return Integer.parseInt(input.get(0));
    }

}
