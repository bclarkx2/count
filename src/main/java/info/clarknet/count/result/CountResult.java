package info.clarknet.count.result;

public class CountResult {

    private int words;
    private int sentences;
    private int paragraphs;

    public CountResult(int words, int sentences, int paragraphs)
    {
        this.words = words;
        this.sentences = sentences;
        this.paragraphs = paragraphs;
    }

    public int words()
    {
        return words;
    }

    public int sentences()
    {
        return sentences;
    }

    public int paragraphs()
    {
        return paragraphs;
    }
}
