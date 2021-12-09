package nl.bramjanssens.dec9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static nl.bramjanssens.util.Util.getLines;

public class Part1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        int sum = lowPoints(heightmap(getLines("dec9/testinput.txt"))).stream().mapToInt(p -> p + 1).sum();
        System.out.println(sum);
    }

    static int[][] heightmap(List<String> lines) {
        int size = lines.size();
        int length = lines.get(0).length();
        int[][] heightmap = new int[size][length];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] heights = line.split("");
            for (int j = 0; j < heights.length; j++) {
                heightmap[i][j] = Integer.parseInt(heights[j]);
            }
        }
        return heightmap;
    }

    static List<Integer> lowPoints(int[][] heightmap) {
        List<Integer> lowPoints = new ArrayList<>();
        for (int i = 0; i < heightmap.length; i++) {
            for (int j = 0; j < heightmap[i].length; j++) {
                int height = heightmap[i][j];
                if (getNeighbors(i, j, heightmap).stream().allMatch(n -> n > height)) {
                    lowPoints.add(height);
                }
            }
        }
        return lowPoints;
    }

    static List<Integer> getNeighbors(int i, int j, int[][] table) {
        List<Integer> neightbours = new ArrayList<>();
        int height = table.length;
        int width = table[0].length;
        if (j > 0) neightbours.add(table[i][j - 1]); // left
        if (j < width - 1) neightbours.add(table[i][j + 1]); // right
        if (i > 0) neightbours.add(table[i - 1][j]); // up
        if (i < height - 1) neightbours.add(table[i + 1][j]); // down

        return neightbours;
    }
}
