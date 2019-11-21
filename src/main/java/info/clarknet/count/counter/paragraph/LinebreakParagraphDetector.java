package info.clarknet.count.counter.paragraph;

import java.util.ArrayList;
import java.util.List;

public final class LinebreakParagraphDetector implements ParagraphDetector {

    @Override
    public List<String> paragraphs(String text) {
        final String trimmed = text.trim();
        final String[] lines = trimmed.split(System.lineSeparator());

        final List<String> paragraphs = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for (final String str : lines)
        {
            if (str.isBlank())
            {
                if (builder.length() > 0)
                {
                    paragraphs.add(builder.toString());
                    builder = new StringBuilder();
                }
            }
            else
            {
                builder.append(str);
            }
        }
        if (builder.length() > 0)
        {
            paragraphs.add(builder.toString());
        }

        return paragraphs;
    }

    @Override
    public long count(String text) {
         final List<String> paragraphs = paragraphs(text);
         return paragraphs.size();
    }
}
