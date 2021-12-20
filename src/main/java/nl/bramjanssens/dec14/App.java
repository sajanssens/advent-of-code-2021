package nl.bramjanssens.dec14;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.Collections.max;
import static java.util.Collections.min;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static nl.bramjanssens.util.Util.getLines;

public class App {
    public static void main(String[] args) {
        var lines = getLines("dec14/input.txt");

        var template = lines.get(0);
        var pairRules = readPairRules(lines);
        var polymerstring = getPolymerstring(template, pairRules, 10);

        var quantities = stream(polymerstring.split("")).collect(groupingBy(s -> s, counting()));
        var max = max(quantities.values());
        var min = min(quantities.values());
        System.out.println(max - min);
    }

    private static Map<String, String> readPairRules(List<String> lines) {
        Map<String, String> pairRules = new HashMap<>();
        for (int i = 2; i < lines.size(); i++) {
            String[] pairRule = lines.get(i).replace(" ", "").split("->");
            pairRules.put(pairRule[0], pairRule[1]);
        }
        return pairRules;
    }

    private static String getPolymerstring(String template, Map<String, String> pairRules, int length) {
        String input = template;
        for (int step = 0; step < length; step++) {
            StringBuilder polymer = new StringBuilder().append(input.charAt(0));
            for (int i = 0; i < input.length() - 1; i++) {
                String pair = input.substring(i, i + 2);
                String element = pairRules.get(pair);
                polymer.append(element).append(pair.charAt(1));
            }
            input = polymer.toString();
        }
        return input;
    }
}
