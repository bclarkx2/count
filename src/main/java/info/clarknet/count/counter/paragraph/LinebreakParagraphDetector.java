package info.clarknet.count.counter.paragraph;

import java.util.ArrayList;
import java.util.List;

public class LinebreakParagraphDetector implements ParagraphDetector {

    @Override
    public List<String> paragraphs(String text) {
        String trimmed = text.trim();
        String[] lines = trimmed.split(System.lineSeparator());

        List<String> paragraphs = new ArrayList<>();

        StringBuilder builder = new StringBuilder();
        for (String str : lines)
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
         List<String> paragraphs = paragraphs(text);
         return paragraphs.size();
    }
}
