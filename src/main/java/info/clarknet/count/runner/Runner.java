package info.clarknet.count.runner;

import info.clarknet.count.counter.Counter;
import info.clarknet.count.counter.DummyCounter;
import info.clarknet.count.input.FileSample;
import info.clarknet.count.input.Sample;
import info.clarknet.count.report.CountReporter;
import info.clarknet.count.report.StreamReporter;
import info.clarknet.count.result.CountResult;

import java.io.IOException;

public class Runner {

    public static void main(String[] args){
        System.out.println("I am a Count runner");

        try{
            Sample sample = FileSample.Of("/home/brian/projects/count/src/main/resources/twenty-thousand-leagues");
            Counter counter = new DummyCounter();
            CountResult result = counter.count(sample);
            CountReporter reporter = new StreamReporter(System.out);
            reporter.report(result);
        }
        catch (IOException e)
        {
            System.out.println("No such file!");
        }
    }
}
