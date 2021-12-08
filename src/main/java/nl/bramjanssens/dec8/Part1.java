package nl.bramjanssens.dec8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static nl.bramjanssens.util.Util.getLines;

public class Part1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        var lines = getLines("dec8/input.txt");
        List<String> outputValues = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split("\\|");
            String[] values = split[1].split(" ");
            outputValues.addAll(stream(values).filter(not(String::isBlank)).toList());
        }
        int size = outputValues.stream()
                .filter(v -> (v.length() == 2 || v.length() == 3 || v.length() == 4 || v.length() == 7))
                .toList().size();
        System.out.println(size);
    }
}
