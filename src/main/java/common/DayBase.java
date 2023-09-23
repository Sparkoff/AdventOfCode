package common;

import java.util.List;
import java.util.function.Function;

public abstract class DayBase<Input, FirstOutput, SecondOutput> implements PuzzleInterface<FirstOutput, SecondOutput> {

    private final PuzzleInput<Input> input;


    public DayBase() {
        String childPackage = getClass().getPackage().getName();
        String childClass = getClass().getSimpleName();
        this.input = new PuzzleInput<>(childPackage, childClass);
    }

    public DayBase(List<String> input) {
        this.input = new PuzzleInput<>(input);
    }


    public Input getInput(Function<List<String>, Input> parser) {
        return this.input.getParsedInput(parser);
    }

}
