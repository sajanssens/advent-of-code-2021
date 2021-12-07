package nl.bramjanssens.dec7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static java.lang.Math.abs;
import static java.util.Arrays.stream;
import static nl.bramjanssens.util.Util.getLines;
import static nl.bramjanssens.util.Util.sum;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        var lines = getLines("dec7/input.txt");
        var positions = stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();
        int length = positions.length;
        var fuelSums = new ArrayList<Integer>(length);

        for (var d = 1; d <= length; d++) {
            final var dest = d;
            // PART ONE:
            // int fuelSum = stream(positions).map(p -> abs(p - dest)).sum();
            // PART TWO:
            var fuelSum = stream(positions).map(p -> sum(abs(p - dest))).sum();

            fuelSums.add(fuelSum);
        }
        System.out.println(fuelSums.stream().mapToInt(s -> s).min().orElse(-1));
    }
}
