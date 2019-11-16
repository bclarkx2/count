package info.clarknet.count.input;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileSample implements Sample {

    private Stream<String> stream;

    public static FileSample Of(String uri) throws IOException {
        Path path = Paths.get(uri);
        try (BufferedReader reader = Files.newBufferedReader(path))
        {
            Stream<String> lines = reader.lines();
            return new FileSample(lines);
        }
    }

    private FileSample(Stream<String> stream) {
        this.stream = stream;
    }

    public Stream<String> lines() {
        return stream;
    }

    public String text() {
        return stream.collect(Collectors.joining(System.lineSeparator()));
    }
}
