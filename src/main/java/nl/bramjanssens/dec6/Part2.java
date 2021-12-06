package nl.bramjanssens.dec6;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URISyntaxException;

import static java.util.Arrays.stream;
import static nl.bramjanssens.util.Util.getLines;

public class Part2 {

    public static final int days = 256;

    public static void main(String[] args) throws URISyntaxException, IOException {
        var lines = getLines("dec6/testinput.txt");
        var numbers = stream(lines.get(0).split(",")).mapToInt(Integer::parseInt).toArray();

        double[] fishcountForAge = new double[9];

        for (int number : numbers) {
            fishcountForAge[number]++;
        }

        for (int d = 1; d <= days; d++) {
            var next = new double[9];

            // all fish of age 0 become fish of 6 and fish of 8
            next[8] = fishcountForAge[0];
            next[6] = fishcountForAge[0];

            // decrease all fish' timers
            for (int i = 1; i <= 8; i++) {
                next[i - 1] += fishcountForAge[i];
            }
            fishcountForAge = next;
        }

        BigDecimal sum = BigDecimal.valueOf(stream(fishcountForAge).sum());
        System.out.println(sum);
    }
}
