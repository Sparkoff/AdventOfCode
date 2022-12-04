package common;

import java.util.List;
import java.util.function.Function;

public abstract class DayBase<Input, Output> implements PuzzleInterface<Output> {

    private final String childPackage = getClass().getPackage().getName();
    private final String childClass = getClass().getSimpleName();

    protected final PuzzleInput<Input> input;


    public DayBase() {
        this.input = new PuzzleInput<>(childPackage, childClass);
    }

    public DayBase(List<String> input) {
        this.input = new PuzzleInput<>(input);
    }

    public Input getInput(Function<List<String>, Input> parser) {
        return this.input.getParsedInput(parser);
    }

    public void printStars() {
        System.out.println("===================");
        System.out.printf("Date: %s.%s%n", childPackage, childClass);
        System.out.println("-------------------");
        System.out.printf("  - First star: %s%n", firstStar());
        System.out.printf("  - Second star: %s%n", secondStar());
        System.out.println("===================");
    }
}
