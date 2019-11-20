package info.clarknet.count.counter.sentence;

import info.clarknet.count.counter.token.PunctuationTokenizer;
import info.clarknet.count.counter.token.Tokenizer;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Basic idea: create a fragment class that has text and subfragments.
 *
 * The number of sentences in a fragment is equal to 1 + (subfragments.count - 1)
 *
 * We start a new fragment whenever we encounter a quote or the token after a punctuation.
 * If we are in the middle of a fragment when we encounter such a situation, we build a
 * subfragment.
 */
public class FragmentSentenceDetector implements SentenceDetector {

    @Override
    public long detect(String text) {

        Tokenizer punctuationTokenizer = PunctuationTokenizer.DefaultPunctuationTokenizer();
        Stream<String> tokenStream = punctuationTokenizer.tokenize(text);

        List<String> tokens = tokenStream.collect(Collectors.toList());

        List<List<String>> fragments = Collections.emptyList();

//        for (String token : tokens)
//        {
//
//        }


        return 0;
    }
}
