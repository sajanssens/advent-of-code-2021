package nl.bramjanssens.dec1a;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class App {

    public static void main(String[] args) throws URISyntaxException, IOException {
        File file = getFileFromResource("dec1a/input.txt");
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

    private static File getFileFromResource(String fileName) throws URISyntaxException {
        URL resource = App.class.getClassLoader().getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return new File(resource.toURI());
        }
    }


}
