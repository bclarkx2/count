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

        String text = sample.text();

        Tokenizer tokenizer = WhitespaceTokenizer.INSTANCE;
        String[] tokens = tokenizer.tokenize(text);
        wordCount = tokens.length;

        try (InputStream modelStream = getClass().getResourceAsStream("/en-sent.bin"))
        {
            SentenceModel sentenceModel = new SentenceModel(modelStream);
            SentenceDetectorME sentenceDetector = new SentenceDetectorME(sentenceModel);
            String[] sentences = sentenceDetector.sentDetect(text);
            sentenceCount = sentences.length;
        }
        catch(IOException e)
        {
            sentenceCount = -1;
        }

        Stream<String> lines = sample.lines();
        long blankLines = lines
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
