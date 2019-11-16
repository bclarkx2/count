package info.clarknet.count.counter;

import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;

public class DummyCounter implements Counter {
    public CountResult count(Sample sample)
    {
        return new CountResult(1, 2, 3);
    }
}
