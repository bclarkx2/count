package info.clarknet.count.counter.token;

import java.util.stream.Stream;

public interface Tokenizer {
    Stream<String> tokenize(String text);
    Stream<String> transform(Stream<String> stream);
}
