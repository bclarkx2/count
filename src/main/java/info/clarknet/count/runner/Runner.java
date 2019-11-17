package info.clarknet.count.runner;

import info.clarknet.count.counter.Counter;
import info.clarknet.count.counter.OpenNLPCounter;
import info.clarknet.count.input.FileSample;
import info.clarknet.count.input.Sample;
import info.clarknet.count.report.CountReporter;
import info.clarknet.count.report.StreamReporter;
import info.clarknet.count.result.CountResult;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Runner {

    private static final String FILE_OPTION = "file";

    private OutputStream outStream;
    private OutputStream errStream;

    public Runner(OutputStream outStream, OutputStream errStream) {
        this.outStream = outStream;
        this.errStream = errStream;
    }

    private OutputStream getOutStream() {
        return this.outStream;
    }

    private OutputStream getErrStream() {
        return this.errStream;
    }

    public void run(String[] args)
    {
        try (PrintWriter errorWriter = new PrintWriter(this.getErrStream());
             PrintWriter outWriter = new PrintWriter(this.getOutStream())) {

            String sourceFile;
            try {
                CommandLine cmd = parseArgs(args);
                sourceFile = cmd.getOptionValue(FILE_OPTION);
            } catch (ParseException e) {
                errorWriter.println(e.getMessage());
                return;
            }

            Sample sample;
            try {
                sample = FileSample.Of(sourceFile);
            } catch (IOException e) {
                errorWriter.println(String.format("Unable to read from file: %s", sourceFile));
                return;
            }

            Counter counter = new OpenNLPCounter();
            CountResult result = counter.count(sample);

            try {
                CountReporter reporter = new StreamReporter(outWriter);
                reporter.report(result);
            }
            catch(IOException e) {
                errorWriter.println("Unable to write results");
            }
        }
    }

    private CommandLine parseArgs(String[] args) throws ParseException
    {
        Options options = new Options();
        options.addOption(Option.builder(FILE_OPTION)
                .required(true)
                .hasArg()
                .build());

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }


    public static void main(String[] args){
        Runner runner = new Runner(System.out, System.err);
        runner.run(args);
    }
}
