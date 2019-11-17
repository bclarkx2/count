package info.clarknet.count.runner;

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

public class Runner {

    private static final String FILE_OPTION = "file";
    private static final String ANALYSIS_OPTION = "counter";
    private static final AnalysisType DEFAULT_ANALYSIS_TYPE = AnalysisType.OpenNLP;

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
            AnalysisType analysisType;
            try {
                CommandLine cmd = parseArgs(args);
                sourceFile = cmd.getOptionValue(FILE_OPTION);
                analysisType = parseAnalysisType(cmd);
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

            Counter counter;
            switch (analysisType) {
                case Dummy:
                    counter = new DummyCounter();
                    break;
                default:
                    counter = new OpenNLPCounter();
            }
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
                .desc("Input file to perform counting analysis on.")
                .build());

        options.addOption(Option.builder(ANALYSIS_OPTION)
                .hasArg()
                .optionalArg(true)
                .type(AnalysisType.class)
                .desc("Type of count analysis to perform on the sample.")
                .build());

        CommandLineParser parser = new DefaultParser();
        return parser.parse(options, args);
    }

    private AnalysisType parseAnalysisType(CommandLine cmd) throws ParseException{

        AnalysisType analysisType;

        if (cmd.hasOption(ANALYSIS_OPTION)) {
            String analysisOption = cmd.getOptionValue(ANALYSIS_OPTION);
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
        Runner runner = new Runner(System.out, System.err);
        runner.run(args);
    }

    public enum AnalysisType
    {
        Dummy,
        OpenNLP
    }
}
