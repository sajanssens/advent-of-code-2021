package nl.bramjanssens.dec2b;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static nl.bramjanssens.util.Util.getLines;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> lines = getLines("dec2/input.txt");

        int pos = 0;
        int depth = 0;
        int aim = 0;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("forward")) {
                int amount = Integer.parseInt(line.split(" ")[1]);
                pos += amount;
                depth += aim * amount;
            }
            if (line.contains("down")) {
                int amount = Integer.parseInt(line.split(" ")[1]);
                aim += amount;
            }
            if (line.contains("up")) {
                int amount = Integer.parseInt(line.split(" ")[1]);
                aim -= amount;
            }
        }

        System.out.println(pos);
        System.out.println(depth);
        System.out.println(pos * depth);
    }
}
