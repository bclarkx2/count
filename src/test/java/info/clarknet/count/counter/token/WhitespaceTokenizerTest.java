package info.clarknet.count.counter.token;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class WhitespaceTokenizerTest {

    private TokenizerTestUtil tester;

    @BeforeEach
    void setup()
    {

        Tokenizer tokenizer = new WhitespaceTokenizer();
        tester = new TokenizerTestUtil(tokenizer);
    }

    @Test
    void tokenize_Empty_CountZero() {
        tester.assertTokenized(
                "",
                Collections.emptyList()
        );
    }

    @Test
    void tokenize_SingleSpace_CountZero() {
        tester.assertTokenized(
                " ",
                Collections.emptyList()
        );
    }

    @Test
    void tokenize_ManySpace_CountZero() {
        tester.assertTokenized(
                "      ",
                Collections.emptyList()
        );
    }

    @Test
    void tokenize_UntrimmedWord_SingleToken() {
        tester.assertTokenized(
                "  word    ",
                Collections.singletonList("word")
        );
    }

    @Test
    void tokenize_OneWord_SingleElementList() {
        tester.assertTokenized(
                "word",
                Collections.singletonList("word")
        );
    }
}