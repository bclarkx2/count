package info.clarknet.count.counter.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

class PunctuationTokenizerTest {

    private TokenizerTestUtil tester;

    @BeforeEach
    void setup()
    {
        Set<String> abbreviations = Set.of("abc.", "cde.");
        Tokenizer tokenizer = new PunctuationTokenizer(abbreviations);
        this.tester = new TokenizerTestUtil(tokenizer);
    }

    @Test
    void tokenize_JustWords_OneTokenPerWord() {
        tester.assertTokenized(
            "this is a sentence",
                Arrays.asList("this", "is", "a", "sentence")
        );
    }

    @Test
    void tokenize_OneWord_OneToken() {
        tester.assertTokenized(
                "word",
                Collections.singletonList("word")
        );
    }

    @Test
    void tokenize_OneAbbreviationInMiddle_AbbvIsOneToken() {
        tester.assertTokenized(
                "Here is abc. in the middle",
                Arrays.asList("Here", "is", "abc.", "in", "the", "middle")
        );
    }

    @Test
    void tokenize_SentenceWithPeriod_PeriodIsTokenized() {
        tester.assertTokenized(
                "The sentence.",
                Arrays.asList("The", "sentence", ".")
        );
    }

    @Test
    void tokenize_EndWithQuote_PeriodAndQuoteTokenized() {
        tester.assertTokenized(
                "I said, \"Hello.\"",
                Arrays.asList("I", "said,", "\"", "Hello", ".", "\"")
        );
    }

    @Test
    void tokenize_MiddleQuote_QuotesExtractedOnBothSides() {
        tester.assertTokenized(
                "She said, \"Don't,\" whispering.",
                Arrays.asList("She", "said,", "\"", "Don't,", "\"", "whispering", ".")
        );
    }

    @Test
    void tokenize_ElidedBegining_SingleQuoteNotExtracted() {
        tester.assertTokenized(
                "That is 'nough said.",
                Arrays.asList("That", "is", "'nough", "said", ".")
        );
    }

    @Test
    void tokenize_SingleHyphenEndWord_HyphenTokenized() {
        tester.assertTokenized(
                "I said-",
                Arrays.asList("I", "said", "-")
        );
    }

    @Test
    void tokenize_MultiHyphenEndWord_HyphensTokenizedTogether() {
        tester.assertTokenized(
                "I said--",
                Arrays.asList("I", "said", "--")
        );
    }
}