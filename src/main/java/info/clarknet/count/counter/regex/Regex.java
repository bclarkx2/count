package info.clarknet.count.counter.regex;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class Regex {

    private static final String PUNCT_REGEX = "[?!.]";
    private static final String QUOTE_REGEX = "[\"]";
    private static final String HYPHENS_REGEX = "[-]+";

    public static final Pattern PUNCT;
    public static final Pattern QUOTE;
    public static final Pattern HYPHENS;
    public static final Pattern BEGINNING_QUOTE;
    public static final Pattern ENDING_QUOTE;
    public static final Pattern ENDING_PUNCT;
    public static final Pattern ENDING_HYPHENS;


    static {
        PUNCT = Pattern.compile(PUNCT_REGEX);
        QUOTE = Pattern.compile(QUOTE_REGEX);
        HYPHENS = Pattern.compile(HYPHENS_REGEX);

        BEGINNING_QUOTE = Pattern.compile(String.format("^%s", QUOTE_REGEX));

        ENDING_QUOTE = Pattern.compile(String.format("%s$", QUOTE_REGEX));
        ENDING_PUNCT = Pattern.compile(String.format("%s$", PUNCT_REGEX));
        ENDING_HYPHENS = Pattern.compile(String.format("%s$", HYPHENS_REGEX));
    }

    public static boolean includesAny(String token, Pattern... patterns)
    {
        return Stream.of(patterns).anyMatch(p -> p.matcher(token).find());
    }

    public static boolean matchesAny(String token, Pattern... patterns)
    {
        return Stream.of(patterns).anyMatch(p -> p.matcher(token).matches());
    }

    public static Optional<Matcher> matcherOfAny(String token, Pattern... patterns)
    {
        return Stream.of(patterns)
                .map(p -> p.matcher(token))
                .filter(Matcher::find)
                .findFirst();
    }

    public static boolean isPunctuation(String token)
    {
        return matchesAny(token, PUNCT, HYPHENS);
    }
}
