package info.clarknet.count.counter.token;

import info.clarknet.count.counter.regex.Regex;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PunctuationTokenizer implements Tokenizer {

    private static final String ABBREVIATIONS_FILE = "/abbreviations";


    private Set<String> abbreviations;

    PunctuationTokenizer(Set<String> abbreviations)
    {
        this.abbreviations = abbreviations;
    }

    public static PunctuationTokenizer DefaultPunctuationTokenizer()
    {
        InputStream abbreviationStream = PunctuationTokenizer.class.getResourceAsStream(ABBREVIATIONS_FILE);
        BufferedReader reader = new BufferedReader(new InputStreamReader(abbreviationStream));

        Set<String> abbreviations = reader
                .lines()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return new PunctuationTokenizer(abbreviations);
    }

    private Set<String> getAbbreviations()
    {
        return abbreviations;
    }

    @Override
    public Stream<String> tokenize(String text) {
        Tokenizer whitespaceTokenizer = new WhitespaceTokenizer();
        Stream<String> whitespaceTokens = whitespaceTokenizer.tokenize(text);
        return transform(whitespaceTokens);
    }

    @Override
    public Stream<String> transform(Stream<String> stream) {
        return stream.flatMap(this::expandToken);
    }

    private Stream<String> expandToken(String token)
    {
        if (token.isEmpty())
        {
            return Stream.empty();
        }

        else if (isAbbreviation(token) || Regex.matchesAny(token, Regex.PUNCT, Regex.QUOTE))
        {
            return Stream.of(token);
        }

        else if (Regex.includesAny(token, Regex.BEGINNING_QUOTE))
        {
            return beginStream(token).flatMap(this::expandToken);
        }

        else if (Regex.includesAny(token, Regex.ENDING_QUOTE, Regex.ENDING_PUNCT))
        {
            return endStream(token).flatMap(this::expandToken);
        }

        else {
            return Stream.of(token);
        }
    }

    private boolean isAbbreviation(String token)
    {
        String lowerCaseToken = token.toLowerCase();
        return getAbbreviations().contains(lowerCaseToken);
    }

    private Stream<String> beginStream(String token){
        return indexSplit(token, 1);
    }

    private Stream<String> endStream(String token) {
        return indexSplit(token, token.length() - 1);
    }

    private Stream<String> indexSplit(String token, int index)
    {
        return Stream.of(
                token.substring(0, index),
                token.substring(index)
        );
    }

//
//    private Stream<String> quoteStream(String token)
//    {
//        int startIndex, endIndex;
//
//        String first = token.substring(0, 1);
//        if (isQuote(first))
//        {
//            startIndex = 0;
//        }
//        else {
//            startIndex = 1;
//        }
//
//        String last = token.substring(token.length() - 1);
//        endIndex = isQuote(last) ? token.length() - 1 : token.length();
//
//        String middle = token.substring(startIndex, endIndex);
//
//        return Stream.of(first, middle, last);
//    }
}