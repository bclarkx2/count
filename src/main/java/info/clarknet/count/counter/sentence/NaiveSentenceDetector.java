package info.clarknet.count.counter.sentence;

import info.clarknet.count.counter.regex.Regex;
import info.clarknet.count.counter.token.PunctuationTokenizer;
import info.clarknet.count.counter.token.Tokenizer;

import java.util.stream.Stream;

public final class NaiveSentenceDetector implements SentenceDetector {

    @Override
    public long detect(String text) {

        final Tokenizer punctuationTokenizer = PunctuationTokenizer.instance();
        final Stream<String> tokenStream = punctuationTokenizer.tokenize(text);

        return tokenStream
                .filter(Regex::isPunctuation)
                .count();
    }

}
