package info.clarknet.count.counter.regex;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;

class RegexTest {

    @Test
    void matcherOfAny() {
        String token = "said--";
        Optional<Integer> index = Regex.indexOfAny(token, Regex.ENDING_PUNCT, Regex.ENDING_QUOTE, Regex.ENDING_HYPHENS);
        if (index.isPresent())
        {
            Assert.assertEquals(4, index.get().intValue());
        }
        else {
            fail("Must have a match.");
        }

    }
}