package info.clarknet.count.counter;

import info.clarknet.count.counter.paragraph.LinebreakParagraphDetector;
import info.clarknet.count.counter.paragraph.ParagraphDetector;
import info.clarknet.count.counter.sentence.NaiveSentenceDetector;
import info.clarknet.count.counter.sentence.SentenceDetector;
import info.clarknet.count.counter.token.Tokenizer;
import info.clarknet.count.counter.token.WhitespaceTokenizer;
import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;

import java.util.stream.Stream;

public final class BasicCounter implements Counter{

    /*********************
     *  Public API
     *********************/

    @Override
    public CountResult count(Sample sample) {
        return new CountResult(
                countWords(sample),
                countSentences(sample),
                countParagraphs(sample)
        );
    }


    /*********************
     *  Private implementation
     *********************/

    private long countWords(Sample sample)
    {
        final String text = sample.text();
        final Tokenizer tokenizer = new WhitespaceTokenizer();
        final Stream<String> tokens = tokenizer.tokenize(text);

        return tokens.count();
    }

    private long countSentences(Sample sample)
    {
        final String text = sample.text();
        final SentenceDetector detector = new NaiveSentenceDetector();
        return detector.detect(text);
    }

    private long countParagraphs(Sample sample)
    {
        final String text = sample.text();
        final ParagraphDetector detector = new LinebreakParagraphDetector();
        return detector.count(text);
    }
}
