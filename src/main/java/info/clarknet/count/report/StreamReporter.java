package info.clarknet.count.report;

import info.clarknet.count.result.CountResult;

import java.io.PrintWriter;

public class StreamReporter implements CountReporter {
    private PrintWriter writer;

    public StreamReporter(PrintWriter writer)
    {
        this.writer = writer;
    }

    @Override
    public void report(CountResult result)
    {
        writer.println(String.format("Words: %d", result.getWords()));
        writer.println(String.format("Sentences: %d", result.getSentences()));
        writer.println(String.format("Paragraphs: %d", result.getParagraphs()));
        writer.flush();
    }
}
