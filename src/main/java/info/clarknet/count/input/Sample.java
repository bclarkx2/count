package info.clarknet.count.input;

import java.util.stream.Stream;

public interface Sample {
    Stream<String> lines();
    String text();
}
