package info.clarknet.count.counter.token;

import info.clarknet.count.counter.regex.Regex;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class PunctuationTokenizer implements Tokenizer {


    /*********************
     *  Constants
     *********************/

    private static final String ABBREVIATIONS_FILE = "/abbreviations";


    /*********************
     *  Fields
     *********************/

    private final Set<String> abbreviations;
    private Set<String> getAbbreviations()
    {
        return abbreviations;
    }


    /*********************
     *  Ctors/Factories
     *********************/

    public static PunctuationTokenizer instance()
    {
        final InputStream abbreviationStream = PunctuationTokenizer.class.getResourceAsStream(ABBREVIATIONS_FILE);
        final BufferedReader reader = new BufferedReader(new InputStreamReader(abbreviationStream));

        final Set<String> abbreviations = reader
                .lines()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        return new PunctuationTokenizer(abbreviations);
    }

    PunctuationTokenizer(Set<String> abbreviations)
    {
        this.abbreviations = abbreviations;
    }


    /*********************
     *  Public API
     *********************/

    @Override
    public Stream<String> tokenize(String text) {
        final Tokenizer whitespaceTokenizer = new WhitespaceTokenizer();
        final Stream<String> whitespaceTokens = whitespaceTokenizer.tokenize(text);
        return transform(whitespaceTokens);
    }

    @Override
    public Stream<String> transform(Stream<String> stream) {
        return stream.flatMap(this::expandToken);
    }


    /*********************
     *  Implementation
     *********************/

    private Stream<String> expandToken(String token)
    {
        if (token.isEmpty())
        {
            return Stream.empty();
        }

        if (isAbbreviation(token) || Regex.matchesAny(token, Regex.PUNCT, Regex.QUOTE, Regex.HYPHENS))
        {
            return Stream.of(token);
        }

        if (Regex.includesAny(token, Regex.BEGINNING_QUOTE))
        {
            return beginStream(token).flatMap(this::expandToken);
        }

        final Optional<Integer> index = Regex.indexOfAny(token, Regex.ENDING_QUOTE, Regex.ENDING_PUNCT, Regex.ENDING_HYPHENS);
        if (index.isPresent())
        {
            Stream<String> split = indexSplit(token, index.get());
            return split.flatMap(this::expandToken);
        }

        return Stream.of(token);
    }

    private boolean isAbbreviation(String token)
    {
        final String lowerCaseToken = token.toLowerCase();
        return getAbbreviations().contains(lowerCaseToken);
    }

    private Stream<String> beginStream(String token){
        return indexSplit(token, 1);
    }

    private Stream<String> indexSplit(String token, int index)
    {
        return Stream.of(
                token.substring(0, index),
                token.substring(index)
        );
    }
}