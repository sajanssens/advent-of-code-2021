package nl.bramjanssens.dec3a;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.bramjanssens.util.Util.getLines;

public class App {

    public static final int WIDTH = 12;

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = getLines("dec3/input.txt");
        int inputSize = lines.size();

        List<List<String>> bits = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++) {
            bits.add(new ArrayList<>());
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < bits.size(); j++) {
                bits.get(j).add(line.charAt(j) + "");
            }
        }
        long[] ones = new long[WIDTH];
        for (int i = 0; i < ones.length; i++) {
            ones[i] = getCount(bits.get(i), "1");
        }
        System.out.println(Arrays.toString(ones));

        String[] gamma = new String[WIDTH];
        String[] epsilon = new String[WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            gamma[i] = "0";
            epsilon[i] = "0";
        }

        for (int i = 0; i < ones.length; i++) {
            if (ones[i] > inputSize / 2) gamma[i] = "1";
            if (ones[i] < inputSize / 2) epsilon[i] = "1";
        }
        System.out.println(Arrays.toString(gamma));
        System.out.println(Arrays.toString(epsilon));
        String g = "";
        for (String s : gamma) {
            g += s;
        }
        String e = "";
        for (String s : epsilon) {
            e += s;
        }

        System.out.println(g);
        int x = Integer.parseInt(g, 2);
        System.out.println(x);

        System.out.println(e);
        int y = Integer.parseInt(e, 2);
        System.out.println(y);

        System.out.println(x * y);
    }

    private static long getCount(List<String> bits, String s) {
        return bits.stream().filter(c -> c.equals(s)).count();
    }
}
