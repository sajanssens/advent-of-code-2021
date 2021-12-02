package nl.bramjanssens.dec1a;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static nl.bramjanssens.util.Util.getFileFromResource;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = getFileFromResource("dec1/input.txt");
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);

        int previous = Integer.MAX_VALUE;
        int count = 0;
        for (String line : lines) {
            int i = Integer.parseInt(line);
            if (i > previous) count++;
            previous = i;
        }

        System.out.println(count);
    }
}
