package nl.bramjanssens.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Util {

    public static File getFileFromResource(String fileName) throws URISyntaxException {
        return new File(Util.class.getClassLoader().getResource(fileName).toURI());
    }

    public static List<String> getLines(String fileName) throws URISyntaxException, IOException {
        return Files.readAllLines(getFileFromResource(fileName).toPath(), StandardCharsets.UTF_8);
    }

    public static List<Integer> loadInput() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            return br.lines().map(Integer::parseInt).collect(toList());
        }
    }
}
