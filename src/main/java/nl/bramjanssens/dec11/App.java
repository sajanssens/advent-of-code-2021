package nl.bramjanssens.dec11;

import lombok.Data;
import lombok.ToString;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.stream;
import static java.util.function.Predicate.not;
import static nl.bramjanssens.util.Util.getLines;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        Fish[][] fishTable = fishTable(getLines("dec11/input.txt"));
        fishStream(fishTable).forEach(f -> f.createNeighbors(fishTable));

        int allFishFlashAtStep = 0;
        for (int t = 1; t <= 10000; t++) {
            fishStream(fishTable).forEach(Fish::charge);
            List<Fish> chargedFish;
            while (!(chargedFish = getChargedFish(fishTable)).isEmpty()) {
                chargedFish.forEach(Fish::tryFlash);
            }

            // for part 2:
            if (fishStream(fishTable).allMatch(Fish::isEmpty)) {
                allFishFlashAtStep = t;
                break;
            }
        }
        System.out.println(fishStream(fishTable).mapToInt(Fish::getFlashCount).sum());
        System.out.println(allFishFlashAtStep);
        print(fishTable);
    }

    private static List<Fish> getChargedFish(Fish[][] fishTable) {
        return fishStream(fishTable).filter(Fish::isFullyCharged).toList();
    }

    private static void print(Fish[][] fishTable) {
        for (int i = 0; i < fishTable.length; i++) {
            for (int j = 0; j < fishTable[i].length; j++) {
                System.out.print(fishTable[i][j]);
            }
            System.out.println();
        }
    }

    static Fish[][] fishTable(List<String> lines) {
        int size = lines.size();
        int length = lines.get(0).length();
        Fish[][] fish = new Fish[size][length];

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] energyLevels = line.split("");
            for (int j = 0; j < energyLevels.length; j++) {
                int energy = Integer.parseInt(energyLevels[j]);
                fish[i][j] = new Fish(i, j, energy);
            }
        }
        return fish;
    }

    private static Stream<Fish> fishStream(Fish[][] fish) {return stream(fish).flatMap(Arrays::stream);}
}

@Data
class Fish {
    private int i, j;
    private int energy;
    @ToString.Exclude
    private List<Fish> neighbors = new ArrayList<>();
    private int flashCount;

    public Fish(int i, int j, int energy) {
        this.i = i;
        this.j = j;
        this.energy = energy;
    }

    void createNeighbors(Fish[][] table) {
        int height = table.length;
        int width = table[0].length;
        if (hasTop()) neighbors.add(table[i - 1][j]); // top
        if (hasTop() && hasRight(width)) neighbors.add(table[i - 1][j + 1]); // top-right
        if (hasRight(width)) neighbors.add(table[i][j + 1]); // right
        if (hasRight(width) && hasBottom(height)) neighbors.add(table[i + 1][j + 1]); // bottom-right
        if (hasBottom(height)) neighbors.add(table[i + 1][j]); // bottom
        if (hasBottom(height) && hasLeft()) neighbors.add(table[i + 1][j - 1]); // bottom-left
        if (hasLeft()) neighbors.add(table[i][j - 1]); // left
        if (hasTop() && hasLeft()) neighbors.add(table[i - 1][j - 1]); // top-left
    }

    @Override public String toString() {
        return energy < 10 ? energy + "" : "*";
    }

    private boolean hasLeft() {
        return j > 0;
    }

    private boolean hasBottom(int height) {
        return i < height - 1;
    }

    private boolean hasRight(int width) {
        return this.j < width - 1;
    }

    private boolean hasTop() {
        return this.i > 0;
    }

    public void charge() {
        energy++;
    }

    boolean isFullyCharged() {
        return this.energy > 9;
    }

    boolean isEmpty() {
        return this.energy == 0;
    }

    void tryFlash() {
        if (energy > 9) {
            this.energy = 0;
            this.flashCount++;
            this.neighbors.stream().filter(not(Fish::isEmpty)).forEach(Fish::charge);
            this.neighbors.forEach(Fish::tryFlash);
        }
    }
}
