package nl.bramjanssens.dec5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.*;
import static java.util.Arrays.stream;
import static nl.bramjanssens.util.Util.getLines;

public class Part2 {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<Vector> vectors = getVectors(getLines("dec5/input.txt"));
        int max = maxFrom(vectors);
        int[][] diagram = new int[max][max];

        for (Vector v : vectors) {
            if (v.isVertical()) {
                int x = v.x1;
                int yStart = min(v.y1, v.y2);
                int yEnd = max(v.y1, v.y2);
                for (int y = yStart; y <= yEnd; y++) {
                    diagram[y][x]++;
                }
            } else if (v.isHorizontal()) {
                int y = v.y1;
                int xStart = min(v.x1, v.x2);
                int xEnd = max(v.x1, v.x2);
                for (int x = xStart; x <= xEnd; x++) {
                    diagram[y][x]++;
                }
            } else if (v.isDiagonal()) {
                int length = abs(v.x1 - v.x2);
                int i = 0;
                int x = v.x1;
                int y = v.y1;
                while (i <= length) {
                    diagram[y][x]++;

                    if (v.x1 < v.x2) x++; // asc
                    else x--; //             desc

                    if (v.y1 < v.y2) y++; // asc
                    else y--; //             desc

                    i++;
                }
            }
        }

        show(diagram);

        long count = stream(diagram)
                .flatMapToInt(Arrays::stream)
                .filter(i -> i > 1)
                .count();
        System.out.println(count);
    }

    private static List<Vector> getVectors(List<String> lines) {
        List<Vector> vectors = new ArrayList<>();
        for (String line : lines) {
            String[] split = split(line);
            int[] coords = getIntStream(split).toArray();
            vectors.add(new Vector(coords[0], coords[1], coords[2], coords[3]));
        }
        return vectors;
    }

    private static int maxFrom(List<Vector> vectors) {
        return vectors.stream()
                .flatMap(v -> Stream.of(v.x1, v.y1, v.x2, v.y2))
                .mapToInt(i -> i).max().orElse(1000) + 1;
    }

    private static String[] split(String line) {
        return line.replace(" ", "").replace("->", ",").split(",");
    }

    private static void show(int[][] diagram) {
        for (int[] ints : diagram) {
            System.out.println(Arrays.toString(ints));
        }
    }

    private static IntStream getIntStream(String[] split) {
        return stream(split).mapToInt(Integer::parseInt);
    }
}
