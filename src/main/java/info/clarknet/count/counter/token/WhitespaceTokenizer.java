package info.clarknet.count.counter.token;

import java.util.Arrays;
import java.util.stream.Stream;

public class WhitespaceTokenizer implements Tokenizer{

    private final String GREEDY_WHITESPACE_REGEX = "\\s+";

    @Override
    public Stream<String> tokenize(String text) {
        String trimmed = text.trim();

        if (trimmed.isBlank()) {
            return Stream.empty();
        }

        return Arrays.stream(trimmed.split(GREEDY_WHITESPACE_REGEX));
    }

    @Override
    public Stream<String> transform(Stream<String> stream) {
        return null;
    }
}
