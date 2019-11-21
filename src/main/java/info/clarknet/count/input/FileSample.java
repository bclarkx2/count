package info.clarknet.count.input;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public final class FileSample implements Sample {

    /*********************
     *  Fields
     *********************/

    private final List<String> lines;

    private List<String> getLines() {
        return lines;
    }

    /*********************
     *  ctors / static factories
     *********************/

    public static FileSample Of(String uri) throws IOException {
        final Path path = Paths.get(uri);
        final List<String> lines = Files.readAllLines(path);
        return new FileSample(lines);
    }

    public static FileSample Of(URL url) throws IOException {
        final String path = url.getPath();
        return Of(path);
    }

    private FileSample(List<String> lines) {
        this.lines = lines;
    }


    /*********************
     *  Public API
     *********************/

    @Override
    public Stream<String> lines() {
        return getLines().stream();
    }

    @Override
    public String text() {
        return String.join(System.lineSeparator(), getLines());
    }
}
