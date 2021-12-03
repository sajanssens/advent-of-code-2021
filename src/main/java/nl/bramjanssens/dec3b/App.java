package nl.bramjanssens.dec3b;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static nl.bramjanssens.util.Util.getLines;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = getLines("dec3/input.txt");
        int width = lines.get(0).length();

        List<String> oxygenLines = new ArrayList<>(lines);
        for (int i = 0; i < width; i++) {
            long[] ones = count(oxygenLines, width, "1");
            String mostCommonValue = ones[i] >= oxygenLines.size() / 2d ? "1" : "0";
            oxygenLines = new ArrayList<>(filter(oxygenLines, i, mostCommonValue));
            if (oxygenLines.size() == 1) break;
        }
        System.out.println(oxygenLines);

        List<String> co2Lines = new ArrayList<>(lines);
        for (int i = 0; i < width; i++) {
            long[] zeroes = count(co2Lines, width, "0");
            String leastCommonValue = zeroes[i] <= co2Lines.size() / 2d ? "0" : "1";
            co2Lines = new ArrayList<>(filter(co2Lines, i, leastCommonValue));
            if (co2Lines.size() == 1) break;
        }
        System.out.println(co2Lines);

        int oxygen = Integer.parseInt(oxygenLines.get(0), 2);
        int co2 = Integer.parseInt(co2Lines.get(0), 2);
        System.out.println(oxygen);
        System.out.println(co2);
        System.out.println(oxygen * co2);
    }

    private static List<String> filter(List<String> lines, int pos, String value) {
        List<String> list = new ArrayList<>();

        for (String line : lines) {
            String charAt = line.charAt(pos) + "";
            if (charAt.equals(value)) {
                list.add(line);
            }
        }
        return list;
    }

    private static long[] count(List<String> lines, int width, String s) {
        List<List<String>> bits = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            bits.add(new ArrayList<>());
        }

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            for (int j = 0; j < bits.size(); j++) {
                bits.get(j).add(line.charAt(j) + "");
            }
        }
        long[] count = new long[width];
        for (int i = 0; i < count.length; i++) {
            count[i] = getCount(bits.get(i), s);
        }
        // System.out.println(Arrays.toString(ones));
        return count;
    }

    private static long getCount(List<String> bits, String s) {
        return bits.stream().filter(c -> c.equals(s)).count();
    }
}
