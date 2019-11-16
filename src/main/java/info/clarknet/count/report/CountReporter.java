package info.clarknet.count.report;

import info.clarknet.count.result.CountResult;

import java.io.IOException;

public interface CountReporter {
    void report(CountResult result) throws IOException;
}
