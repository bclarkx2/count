package info.clarknet.count;

import info.clarknet.count.counter.Counter;
import info.clarknet.count.counter.RealCounter;
import info.clarknet.count.input.FileSample;
import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;
import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

class CountIT {

    private static Map<String, CountResult> tests = Map.ofEntries(
            Map.entry("/blank", new CountResult(0, 0, 0)),
            Map.entry("/oneword", new CountResult(1, 0, 1)),
            Map.entry("/onewordsentence", new CountResult(1, 1, 1)),
            Map.entry("/twenty-thousand-leagues", new CountResult(92, 7, 3)),
            Map.entry("/singlespace", new CountResult(0,0,0)),
            Map.entry("/normalsentence", new CountResult(10, 1, 1)),
            Map.entry("/multisentence", new CountResult(18, 3, 1)),
            Map.entry("/endwithquote", new CountResult(4, 1, 1)),
            Map.entry("/middlequote", new CountResult(4, 1, 1)),
            Map.entry("/middlequoteexclaim", new CountResult(4, 1, 1)),
            Map.entry("/multisentencequote", new CountResult(18, 3, 1))
    );

    private static Set<Map.Entry<String, CountResult>> testCases() {
        return tests.entrySet();
    }

    private boolean useWords = true;
    private boolean useSentences = true;
    private boolean useParagraphs = false;


    @ParameterizedTest
    @MethodSource("testCases")
    void TestAll(Map.Entry<String, CountResult> testCase)
    {
        String uri = testCase.getKey();
        CountResult expected = testCase.getValue();

        Counter counter = new RealCounter();
        assertCount(expected, counter, uri);
    }

    private void assertCount(CountResult expected, Counter counter, String uri)
    {
        try {
            CountResult actual = runCount(counter, uri);
            String msg = String.format("\nTest: \n%s\nExpected: \n%s\nActual: \n%s\n", uri, expected, actual);

            Assert.assertTrue(msg, expected.sameAs(actual, useWords, useSentences, useParagraphs));
        }
        catch (IOException e)
        {
            Assert.fail("Encountered IO exception reading test resource.");
        }
    }

    private CountResult runCount(Counter counter, String resource) throws IOException
    {
        URL url = Optional
                .ofNullable(this.getClass().getResource(resource))
                .orElseThrow(IOException::new);

        Sample sample = FileSample.Of(url);
        return counter.count(sample);

    }
}
