package nl.bramjanssens.dec8;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.joining;
import static nl.bramjanssens.util.Util.getLines;

public class Part2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        var lines = getLines("dec8/input.txt");

        List<String[]> signalsLines = new ArrayList<>();
        List<String[]> outputValuesLines = new ArrayList<>();
        List<Integer> results = new ArrayList<>();

        // read signal lines and output value lines:
        for (String line : lines) {
            String[] splitLine = line.split("\\|");
            String[] signals = splitLine[0].split(" ");
            signalsLines.add(signals);

            String[] values = splitLine[1].split(" ");
            outputValuesLines.add(values);
        }

        for (int lineNr = 0; lineNr < lines.size(); lineNr++) {
            Map<String, Integer> lookup = new HashMap<>();
            String[] signalsLine = signalsLines.get(lineNr);

            // the wires are not sorted, sort them first:
            List<String> signals = new ArrayList<>(stream(signalsLine).map(Part2::sort).toList());

            // 1) find 1, 4, 7 and 8:
            for (int signalNr = 0; signalNr < signals.size(); signalNr++) {
                String signalPattern = signals.get(signalNr);
                if (signalPattern == null) continue;

                if (signalPattern.length() == 2) {
                    lookup.put(signalPattern, 1);
                    removeFrom(signals, signalPattern);
                } else if (signalPattern.length() == 3) {
                    lookup.put(signalPattern, 7);
                    removeFrom(signals, signalPattern);
                } else if (signalPattern.length() == 4) {
                    lookup.put(signalPattern, 4);
                    removeFrom(signals, signalPattern);
                } else if (signalPattern.length() == 7) {
                    lookup.put(signalPattern, 8);
                    removeFrom(signals, signalPattern);
                }
            }

            // 2) find where one is also contained
            String one = getKey(lookup, 1);

            List<String> signalsWithLengthFive = getByLength(signals, 5);
            List<String> signalsWithLengthSix = getByLength(signals, 6); ;

            //      if one is contained in signals of length 5, it must be a 3
            for (String signal : signalsWithLengthFive) {
                if (containsAll(signal, one)) {
                    lookup.put(signal, 3);
                    removeFrom(signals, signal);
                    break;
                }
            }

            //      if one is contained in signals of length 6, it must be a 6
            for (String signal : signalsWithLengthSix) {
                if (!containsAll(signal, one)) {
                    lookup.put(signal, 6);
                    removeFrom(signals, signal);
                    break;
                }
            }

            // 3) for the two remaining signals of length 6, if it contains the three, it must be a 9
            signalsWithLengthSix = getByLength(signals, 6);
            String three = getKey(lookup, 3);

            for (String signal : signalsWithLengthSix) {
                if (containsAll(signal, three)) {
                    lookup.put(signal, 9);
                    removeFrom(signals, signal);
                    break;
                }
            }

            // the other signal of length 6 must be a zero
            String zero = getByLength(signals, 6).get(0);
            lookup.put(zero, 0);
            removeFrom(signals, zero);

            // 4) we have 2 and 5 remaining now.
            //      find the missing wire in the six; that is the upper right segment
            String missingWireInSix = findMissingWire(getKey(lookup, 6));
            for (String signal : signals) {
                if (signal != null && signal.contains(missingWireInSix)) {
                    // the signal containing the upper right segment must be the 2
                    lookup.put(signal, 2);
                    removeFrom(signals, signal);
                    break;
                }
            }

            //      the last one must be the 5:
            String lastSignal = signals.stream().filter(Objects::nonNull).findAny().orElseThrow(() -> new NoSuchElementException("lastSignal not found"));
            lookup.put(lastSignal, 5);
            removeFrom(signals, lastSignal);

            // Finally, determine the output for this line
            String[] outputValues = outputValuesLines.get(lineNr);

            //      the wires are not sorted, sort them first
            List<String> outputValuesSorted = new ArrayList<>(stream(outputValues).filter(not(String::isBlank)).map(Part2::sort).toList());

            int a = lookup.get(outputValuesSorted.get(0)) * 1000;
            int b = lookup.get(outputValuesSorted.get(1)) * 100;
            int c = lookup.get(outputValuesSorted.get(2)) * 10;
            int d = lookup.get(outputValuesSorted.get(3));
            int totalOutput = a + b + c + d;
            results.add(totalOutput);
        }

        System.out.println(results.stream().mapToInt(i -> i).sum());
    }

    private static List<String> getByLength(List<String> signalPatterns, int i) {
        return signalPatterns.stream().filter(Objects::nonNull).filter(s -> s.length() == i).toList();
    }

    private static String findMissingWire(String input) {
        for (String c : "abcdefg".split("")) {
            if (!input.contains(c)) {
                return c;
            }
        }
        return "";
    }

    private static <K, V> K getKey(Map<K, V> fromTable, V value) {
        return fromTable.entrySet().stream()
                .filter(e -> e.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("key not found"))
                .getKey();
    }

    private static boolean containsAll(String input, String search) {
        for (String c : search.split("")) {
            if (!input.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private static void removeFrom(List<String> signalPatterns, String s) {
        signalPatterns.set(signalPatterns.indexOf(s), null);
    }

    private static String sort(String s) {
        return stream(s.split("")).sorted().collect(joining());
    }
}
