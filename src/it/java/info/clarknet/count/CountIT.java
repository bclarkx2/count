package info.clarknet.count;

import info.clarknet.count.counter.Counter;
import info.clarknet.count.counter.OpenNLPCounter;
import info.clarknet.count.input.FileSample;
import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

class CountIT {

    private static Map<String, CountResult> tests = Map.ofEntries(
        Map.entry("/blank", new CountResult(0, 0, 1)),
        Map.entry("/oneword", new CountResult(1, 1, 1))
    );

    private static Set<Map.Entry<String, CountResult>> testCases() {
        return tests.entrySet();
    }


    @ParameterizedTest
    @MethodSource("testCases")
    void TestAll(Map.Entry<String, CountResult> testCase)
    {
        String uri = testCase.getKey();
        CountResult expected = testCase.getValue();

        Counter counter = new OpenNLPCounter();
        assertCount(expected, counter, uri);
    }

    private void assertCount(CountResult expected, Counter counter, String uri)
    {
        try {
            CountResult actual = runCount(counter, uri);
            String msg = String.format("\nTest: \n%s\nExpected: \n%s\nActual: \n%s\n", uri, expected, actual);
            Assert.assertEquals(msg, expected, actual);
        }
        catch (IOException e)
        {
            Assert.fail("Encountered IO exception reading test resource.");
        }
    }

    private CountResult runCount(Counter counter, String uri) throws IOException
    {
        String path = this.getClass().getResource(uri).getPath();

        Sample sample = FileSample.Of(path);
        return counter.count(sample);

    }
}
