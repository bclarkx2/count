package info.clarknet.count.counter.regex;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class Regex {

    /*********************
     *  Private constants
     *********************/

    private static final String PUNCT_REGEX = "[?!.]";
    private static final String QUOTE_REGEX = "[\"]";
    private static final String HYPHENS_REGEX = "[-]+";


    /*********************
     *  Public constants
     *********************/

    public static final Pattern PUNCT;
    public static final Pattern QUOTE;
    public static final Pattern HYPHENS;
    public static final Pattern BEGINNING_QUOTE;
    public static final Pattern ENDING_QUOTE;
    public static final Pattern ENDING_PUNCT;
    public static final Pattern ENDING_HYPHENS;

    public static final String GREEDY_WHITESPACE_REGEX = "\\s+";


    /* *******************
     *  Pre-compiled patterns (initialized at class load)
     *********************/

    static {
        PUNCT = Pattern.compile(PUNCT_REGEX);
        QUOTE = Pattern.compile(QUOTE_REGEX);
        HYPHENS = Pattern.compile(HYPHENS_REGEX);

        BEGINNING_QUOTE = compileBeginning(QUOTE_REGEX);

        ENDING_QUOTE = compileEnding(QUOTE_REGEX);
        ENDING_PUNCT = compileEnding(PUNCT_REGEX);
        ENDING_HYPHENS = compileEnding(HYPHENS_REGEX);
    }


    /*********************
     *  Compilation helpers
     *********************/

    private static Pattern compileBeginning(String regex)
    {
        return Pattern.compile(String.format("^%s", regex));
    }

    private static Pattern compileEnding(String regex)
    {
        return Pattern.compile(String.format("%s$", regex));
    }


    /*********************
     *  Public API
     *********************/

    public static boolean includesAny(String token, Pattern... patterns)
    {
        return Stream.of(patterns).anyMatch(p -> p.matcher(token).find());
    }

    public static boolean matchesAny(String token, Pattern... patterns)
    {
        return Stream.of(patterns).anyMatch(p -> p.matcher(token).matches());
    }

    public static Optional<Integer> indexOfAny(String token, Pattern... patterns)
    {
        return Stream.of(patterns)
                .map(p -> p.matcher(token))
                .filter(Matcher::find)
                .findAny()
                .map(Matcher::start);
    }

    public static boolean isPunctuation(String token)
    {
        return matchesAny(token, PUNCT, HYPHENS);
    }
}
