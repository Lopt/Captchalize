package cap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {

    private CommandLineParser parser = new GnuParser();
    private Options options = new Options();
    private String usage = "captchalize [options] [image1, [image2, [image3] ...]]";

    public ArgumentParser() {
    }

    public ArgumentParser(final CommandLineParser parser) {
        assert parser != null;

        this.parser = parser;
    }

    public ArgumentParser(final CommandLineParser parser, Options options) {
        assert parser != null;
        assert options != null;

        this.parser = parser;
        this.options = options;
    }

    public CommandLine parse(final String[] arguments) throws ParseException {
        return this.parser.parse(this.options, arguments);
    }

    public CommandLine parse(final String[] arguments, boolean stopAtNonOption) throws ParseException {
        return this.parser.parse(this.options, arguments, stopAtNonOption);
    }

    public void printHelp() {
        (new HelpFormatter()).printHelp(this.usage, this.options);
    }

    public Options getOptions() {
        return this.options;
    }

    public void setOptions(final Options options) {
        this.options = options;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(final String usage) {
        this.usage = usage;
    }
}
