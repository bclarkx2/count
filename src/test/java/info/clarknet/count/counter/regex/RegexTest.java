package info.clarknet.count.counter.regex;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;

class RegexTest {

    @Test
    void matcherOfAny() {
        String token = "said--";
        Optional<Matcher> matcher = Regex.matcherOfAny(token, Regex.ENDING_PUNCT, Regex.ENDING_QUOTE, Regex.ENDING_HYPHENS);
        if (matcher.isPresent())
        {
            Assert.assertEquals(4, matcher.get().start());
        }
        else {
            fail("Must have a match.");
        }

    }
}