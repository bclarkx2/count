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

    public long words()
    {
        return words;
    }

    public long sentences()
    {
        return sentences;
    }

    public long paragraphs()
    {
        return paragraphs;
    }
}
