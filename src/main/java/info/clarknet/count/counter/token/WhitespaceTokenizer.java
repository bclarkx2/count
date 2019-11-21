package info.clarknet.count.counter.token;

import info.clarknet.count.counter.regex.Regex;

import java.util.Arrays;
import java.util.stream.Stream;

public final class WhitespaceTokenizer implements Tokenizer{

    /*********************
     *  Public API
     *********************/

    @Override
    public Stream<String> tokenize(String text) {
        final String trimmed = text.trim();

        if (trimmed.isBlank()) {
            return Stream.empty();
        }

        final String[] lines = trimmed.split(Regex.GREEDY_WHITESPACE_REGEX);
        return Arrays.stream(lines);
    }

    @Override
    public Stream<String> transform(Stream<String> tokens) {
        return tokens
                .flatMap(this::tokenize);
    }
}
