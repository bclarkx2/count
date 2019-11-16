package info.clarknet.count.report;

import info.clarknet.count.counter.Counter;
import info.clarknet.count.result.CountResult;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.stream.Stream;

public class StreamReporter implements CountReporter {
    private OutputStream stream;

    public StreamReporter(OutputStream stream)
    {
        this.stream = stream;
    }

    public void report(CountResult result)
    {
        try (PrintWriter writer = new PrintWriter(stream))
        {
            writer.println(String.format("Words: %d", result.words()));
            writer.println(String.format("Sentences: %d", result.sentences()));
            writer.println(String.format("Paragraphs: %d", result.paragraphs()));
            writer.flush();
        }
    }
}
