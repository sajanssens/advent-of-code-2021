package nl.bramjanssens.dec6;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;
import static nl.bramjanssens.util.Util.getLines;

public class Part1 {
    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = getLines("dec6/testinput.txt");
        List<Fish> fish = new ArrayList<>(stream(lines.get(0).split(",")).map(Integer::parseInt).map(Fish::new).toList());
        for (int day = 1; day <= 256; day++) {
            fish.forEach(Fish::decAge);
            List<Fish> newFish = fish.stream().filter(Fish::isReset).map(f -> new Fish(8)).toList();
            fish.addAll(newFish);
        }
        System.out.println(fish.size());
    }
}
