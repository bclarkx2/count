package info.clarknet.count.result;

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
}
