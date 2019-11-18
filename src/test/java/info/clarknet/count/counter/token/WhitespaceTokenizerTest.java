package info.clarknet.count.counter.token;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;

class WhitespaceTokenizerTest {

    private WhitespaceTokenizer tokenizer;

    @BeforeEach
    void setup()
    {
        tokenizer = new WhitespaceTokenizer();
    }

    @Test
    void tokenize_Empty_CountZero() {
        assertTokenized(
                "",
                Collections.emptyList()
        );
    }

    @Test
    void tokenize_SingleSpace_CountZero() {
        assertTokenized(
                " ",
                Collections.emptyList()
        );
    }

    @Test
    void tokenize_ManySpace_CountZero() {
        assertTokenized(
                "      ",
                Collections.emptyList()
        );
    }

    @Test
    void tokenize_UntrimmedWord_SingleToken() {
        assertTokenized(
                "  word    ",
                Collections.singletonList("word")
        );
    }

    @Test
    void tokenize_OneWord_SingleElementList() {
        assertTokenized(
                "word",
                Collections.singletonList("word")
        );
    }

    private void assertTokenized(String text, List<String> expected)
    {
        List<String> actual = tokenizer.tokenize(text).collect(Collectors.toList());
        Assert.assertEquals(expected, actual);
    }
}