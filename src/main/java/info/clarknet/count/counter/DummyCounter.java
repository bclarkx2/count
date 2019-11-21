package info.clarknet.count.counter;

import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;

public final class DummyCounter implements Counter {

    @Override
    public CountResult count(Sample sample)
    {
        return new CountResult(1, 2, 3);
    }
}
