package info.clarknet.count.counter.paragraph;

import java.util.List;

public interface ParagraphDetector {
    List<String> paragraphs(String text);
    long count(String text);
}
