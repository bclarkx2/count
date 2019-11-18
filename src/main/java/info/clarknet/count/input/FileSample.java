package info.clarknet.count.input;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class FileSample implements Sample {

    private final List<String> lines;

    public static FileSample Of(String uri) throws IOException {
        Path path = Paths.get(uri);
        try (BufferedReader reader = Files.newBufferedReader(path))
        {
            Stream<String> stream = reader.lines();
            List<String> lines = stream.collect(Collectors.toList());
            return new FileSample(lines);
        }
    }

    private FileSample(List<String> lines) {
        this.lines = lines;
    }

    private List<String> getLines()
    {
        return this.lines;
    }

    @Override
    public Stream<String> lines() {
        return getLines().stream();
    }

    @Override
    public String text() {
        return String.join(System.lineSeparator(), getLines());
    }
}
