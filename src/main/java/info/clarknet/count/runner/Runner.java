package info.clarknet.count.runner;

import info.clarknet.count.counter.BasicCounter;
import info.clarknet.count.counter.Counter;
import info.clarknet.count.counter.DummyCounter;
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

public final class Runner {

    /********************
     * Constants
     ********************/

    private static final String FILE_OPTION = "file";
    private static final String ANALYSIS_OPTION = "counter";
    private static final AnalysisType DEFAULT_ANALYSIS_TYPE = AnalysisType.Basic;


    /********************
     * Fields
     ********************/

    private final OutputStream outStream;
    private final OutputStream errStream;

    private OutputStream getOutStream() {
        return this.outStream;
    }

    private OutputStream getErrStream() {
        return this.errStream;
    }


    /********************
     * ctors / static factories
     ********************/

    public Runner(OutputStream outStream, OutputStream errStream) {
        this.outStream = outStream;
        this.errStream = errStream;
    }


    /********************
     * Public API
     ********************/

    public void run(String[] args)
    {
        // Establish resources needed for the run
        try (final PrintWriter errorWriter = new PrintWriter(this.getErrStream());
             final PrintWriter outWriter = new PrintWriter(this.getOutStream())) {

            // Parse command line to determine what needs to be done
            final String sourceFile;
            final AnalysisType analysisType;
            try {
                final CommandLine cmd = parseArgs(args);
                sourceFile = cmd.getOptionValue(FILE_OPTION);
                analysisType = parseAnalysisType(cmd);
            } catch (ParseException e) {
                errorWriter.println(e.getMessage());
            }

            // Attempt to read in source file
            final Sample sample;
            try {
                sample = FileSample.Of(sourceFile);
            } catch (IOException e) {
                errorWriter.println(String.format("Unable to read from file: %s", sourceFile));
                return;
            }

            // Execute counting analysis
            final Counter counter;
            switch (analysisType) {
                case Dummy:
                    counter = new DummyCounter();
                    break;
                case OpenNLP:
                    counter = new OpenNLPCounter();
                    break;
                default:
                    counter = new BasicCounter();
            }
            final CountResult result = counter.count(sample);

            // Report on results
            try {
                final CountReporter reporter = new StreamReporter(outWriter);
                reporter.report(result);
            }
            catch(IOException e) {
                errorWriter.println("Unable to write results");
            }
        }
    }


    /********************
     * Private implementation
     ********************/

    private CommandLine parseArgs(String[] args) throws ParseException
    {
        final Options options = new Options();
        options.addOption(Option.builder(FILE_OPTION)
                .required(true)
                .hasArg()
                .desc("Input file to perform counting analysis on.")
                .build());

        options.addOption(Option.builder(ANALYSIS_OPTION)
                .hasArg()
                .optionalArg(true)
                .type(AnalysisType.class)
                .desc("Type of count analysis to perform on the sample.")
                .build());

        final CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    private AnalysisType parseAnalysisType(CommandLine cmd) throws ParseException{

        final AnalysisType analysisType;

        if (cmd.hasOption(ANALYSIS_OPTION)) {
            final String analysisOption = cmd.getOptionValue(ANALYSIS_OPTION);
            try {
                analysisType = AnalysisType.valueOf(analysisOption);
            }
            catch (IllegalArgumentException e) {
                String msg = String.format("\"%s\" is not a valid analysis type. See Runner.AnalysisTypes.", analysisOption);
                throw new ParseException(msg);
            }
        }
        else{
            analysisType = DEFAULT_ANALYSIS_TYPE;
        }
        return analysisType;
    }

    public static void main(String[] args){
        final Runner runner = new Runner(System.out, System.err);
        runner.run(args);
    }

    public enum AnalysisType
    {
        Dummy,
        OpenNLP,
        Basic
    }
}
