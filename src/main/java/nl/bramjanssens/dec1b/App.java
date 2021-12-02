package nl.bramjanssens.dec1b;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static nl.bramjanssens.util.Util.getFileFromResource;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = getFileFromResource("dec1/input.txt");
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        int previousSum = Integer.MAX_VALUE;
        int count = 0;
        for (int i = 0; i < lines.size() - 2; i++) {
            int i1 = Integer.parseInt(lines.get(i));
            int i2 = Integer.parseInt(lines.get(i + 1));
            int i3 = Integer.parseInt(lines.get(i + 2));
            int sum = i1 + i2 + i3;
            if (sum > previousSum) count++;
            previousSum = sum;
        }

        System.out.println(count);
    }


}
