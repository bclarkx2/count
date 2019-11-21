package info.clarknet.count.counter;

import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.WhitespaceTokenizer;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public class OpenNLPCounter implements Counter {

    @Override
    public CountResult count(Sample sample) {

        long wordCount;
        long sentenceCount;
        long paragraphCount;

        final String text = sample.text();

        final Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        final String[] tokens = tokenizer.tokenize(text);
        wordCount = tokens.length;

        try (final InputStream modelStream = getClass().getResourceAsStream("/en-sent.bin"))
        {
            final SentenceModel sentenceModel = new SentenceModel(modelStream);
            final SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            final String[] sentences = sentenceDetector.sentDetect(text);
            sentenceCount = sentences.length;
        }
        catch(IOException e)
        {
            sentenceCount = -1;
        }

        final Stream<String> lines = sample.lines();
        final long blankLines = lines
                .filter(String::isBlank)
                .count();
        paragraphCount = blankLines + 1;

        return new CountResult(
                wordCount,
                sentenceCount,
                paragraphCount
        );
    }
}
