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
        this.createDefaultOptions();
    }

    public ArgumentParser(final CommandLineParser parser) {
        assert parser != null;

        this.parser = parser;
        this.createDefaultOptions();
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

    public void createDefaultOptions() {
        Option serverMode = OptionBuilder
            .withLongOpt("server-mode")
            .withDescription("running in server mode.")
            .create("s");

        Option parseMode = OptionBuilder
            .withLongOpt("find")
            .withDescription("find and analyze a CAPTCHA from a website.")
            .hasArg()
            .withArgName("url")
            .create("f");

        Option captchaSystem = OptionBuilder
            .withLongOpt("captcha-system")
            .withDescription("use a specific CAPTCHA system.")
            .hasArg()
            .withArgName("system_name")
            .create("y");

        Option help = OptionBuilder
            .withLongOpt("help")
            .withDescription("print this message.")
            .create();

        Options op = this.options;
        op.addOption(serverMode);
        op.addOption(parseMode);
        op.addOption(captchaSystem);
        op.addOption(help);
    }

    public void printHelp() {
        (new HelpFormatter()).printHelp(this.usage, this.options);
    }

    public Options getDefaultOptions() {
        return this.options;
    }

    public void setDefaultOptions(final Options options) {
        this.options = options;
    }

    public String getUsage() {
        return this.usage;
    }

    public void setUsage(final String usage) {
        this.usage = usage;
    }
}
