package nl.bramjanssens.dec9;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Collections.reverse;
import static java.util.Comparator.comparingInt;
import static nl.bramjanssens.dec9.Part1.heightmap;
import static nl.bramjanssens.util.Util.getLines;

public class Part2 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        int[][] heightmap = heightmap(getLines("dec9/input.txt"));
        List<Basin> basins = findBasins(lowPoints(heightmap), heightmap);
        List<Basin> basinsSorted = sortBySize(basins);
        Integer answer = multiplyFirstThreeSizes(basinsSorted);
        System.out.println(answer);
    }

    private static List<Basin> findBasins(List<Point> lowPoints, int[][] heightmap) {
        List<Basin> basins = new ArrayList<>();

        for (Point lowPoint : lowPoints) {
            Set<Point> basinPoints = new HashSet<>();
            basinPoints.add(lowPoint);
            findBasinPoints(heightmap, lowPoint, basinPoints);
            basins.add(new Basin(basinPoints));
        }
        return basins;

    }

    private static void findBasinPoints(int[][] heightmap, Point p, Set<Point> basinPoints) {
        List<Point> neighbors = getNeighbors(p.i(), p.j(), heightmap);
        for (Point neighbor : neighbors) {
            if (neighbor.height() != 9 && neighbor.height() > p.height()) {
                basinPoints.add(neighbor);
                findBasinPoints(heightmap, neighbor, basinPoints);
            }
        }
    }

    static List<Point> lowPoints(int[][] heightmap) {
        List<Point> lowPoints = new ArrayList<>();
        for (int i = 0; i < heightmap.length; i++) {
            for (int j = 0; j < heightmap[i].length; j++) {
                int height = heightmap[i][j];
                if (getNeighbors(i, j, heightmap).stream().allMatch(n -> n.height() > height)) {
                    lowPoints.add(new Point(i, j, height));
                }
            }
        }
        return lowPoints;
    }

    static List<Point> getNeighbors(int i, int j, int[][] table) {
        List<Point> neighbors = new ArrayList<>();
        int height = table.length;
        int width = table[0].length;

        if (j > 0) neighbors.add(new Point(i, j - 1, table[i][j - 1])); // left
        if (j < width - 1) neighbors.add(new Point(i, j + 1, table[i][j + 1])); // right
        if (i > 0) neighbors.add(new Point(i - 1, j, table[i - 1][j])); // up
        if (i < height - 1) neighbors.add(new Point(i + 1, j, table[i + 1][j])); // down

        return neighbors;
    }

    private static List<Basin> sortBySize(List<Basin> basins) {
        List<Basin> basinsSorted = new ArrayList<>(basins.stream().sorted(comparingInt(b -> b.points().size())).toList());
        reverse(basinsSorted);
        return basinsSorted;
    }

    private static Integer multiplyFirstThreeSizes(List<Basin> basinsSorted) {
        return basinsSorted.stream().limit(3).map(b -> b.points().size()).reduce(1, (a, b) -> a * b);
    }
}

record Point(int i, int j, int height) {}

record Basin(Set<Point> points) {}

