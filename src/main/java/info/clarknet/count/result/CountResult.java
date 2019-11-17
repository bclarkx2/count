package info.clarknet.count.result;

import java.util.Objects;

public class CountResult {

    private long words;
    private long sentences;
    private long paragraphs;

    public CountResult(long words, long sentences, long paragraphs)
    {
        this.words = words;
        this.sentences = sentences;
        this.paragraphs = paragraphs;
    }

    public long getWords()
    {
        return words;
    }

    public long getSentences()
    {
        return sentences;
    }

    public long getParagraphs()
    {
        return paragraphs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CountResult that = (CountResult) o;
        return words == that.words &&
                sentences == that.sentences &&
                paragraphs == that.paragraphs;
    }

    @Override
    public int hashCode() {
        return Objects.hash(words, sentences, paragraphs);
    }

    @Override
    public String toString() {
        return "CountResult{" +
                "words=" + words +
                ", sentences=" + sentences +
                ", paragraphs=" + paragraphs +
                '}';
    }
}
