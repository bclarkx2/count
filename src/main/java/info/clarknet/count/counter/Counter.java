package info.clarknet.count.counter;

import info.clarknet.count.input.Sample;
import info.clarknet.count.result.CountResult;

public interface Counter {
    CountResult count(Sample sample);
}
