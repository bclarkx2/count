package info.clarknet.count.counter.token;

import org.junit.Assert;

import java.util.List;
import java.util.stream.Collectors;

class TokenizerTestUtil {

    private Tokenizer tokenizer;

    TokenizerTestUtil(Tokenizer tokenizer)
    {
        this.tokenizer = tokenizer;
    }

    private Tokenizer getTokenizer()
    {
        return this.tokenizer;
    }

    void assertTokenized(String text, List<String> expected)
    {
        Tokenizer tokenizer = getTokenizer();
        List<String> actual = tokenizer.tokenize(text).collect(Collectors.toList());
        Assert.assertEquals(expected, actual);
    }

}
