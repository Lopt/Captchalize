package cap;

import java.net.MalformedURLException;
import java.net.URL;

import cap.systems.UnknownCaptchaSystem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineInterpreter {

    private ArgumentParser parser = new ArgumentParser();

    public CommandLineInterpreter() {
        this.createDefaultOptions();
    }

    public CommandLineInterpreter(ArgumentParser parser) {
        this.parser = parser;
        this.createDefaultOptions();
    }

    public void run(String[] arguments) {
        try {
            this.interpret(this.parser.parse(arguments));
        } catch (ParseException exception) {
            this.parser.printHelp();
        }
    }

    protected void createDefaultOptions() {
        Option serverMode = OptionBuilder
            .withLongOpt("server-mode")
            .withDescription("running in server mode.")
            .create("s");

        Option maxImageSize = OptionBuilder
            .withLongOpt("max-image-size")
            .withDescription("maximum upload size of an image. default: 512 kBytes)")
            .hasArg()
            .withArgName("kilobytes")
            .create();

        Option maxRequestSize = OptionBuilder
            .withLongOpt("max-request-size")
            .withDescription("maximum upload size of all images on a single request. (default 3072 kBytes)")
            .hasArg()
            .withArgName("kilobytes")
            .create();

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

        Option debugMode = OptionBuilder
            .withLongOpt("debug")
            .withDescription("print step by step results.")
            .create("d");

        Option debugGUIMode = OptionBuilder
            .withLongOpt("debug-gui")
            .withDescription("offer a gui for step by step results.")
            .create("g");

        Option help = OptionBuilder
            .withLongOpt("help")
            .withDescription("print this message.")
            .create();

        Options op = this.parser.getOptions();

        op.addOption(serverMode);
        op.addOption(maxImageSize);
        op.addOption(maxRequestSize);
        op.addOption(parseMode);
        op.addOption(captchaSystem);
        op.addOption(debugMode);
        op.addOption(debugGUIMode);
        op.addOption(help);

        this.parser.setOptions(op);
    }

    protected void interpret(CommandLine line) {
        if (line.hasOption("help")) {
            this.parser.printHelp();
            return;
        }

        RunArguments args = RunArguments.getInstance();

        args.setDebugMode(line.hasOption("debug"));
        args.setDebugGui(line.hasOption("debug-gui"));

        if (line.hasOption("captcha-system")) {
            args.setCaptchaSystem(UnknownCaptchaSystem.findSystem(line.getOptionValue("captcha-system")));
        }

        if (line.hasOption("server-mode")) {
            args.setServerMode(true);

            if (line.hasOption("max-image-size")) {
                args.setMaxImageSize(Long.parseLong(line.getOptionValue("max-image-size")) * 1024);
            }
            if (line.hasOption("max-request-size")) {
                args.setMaxRequestSize(Long.parseLong(line.getOptionValue("max-request-size")) * 1024);
            }

        } else if (line.hasOption("find")) {
            try {
                args.setFindOn(new URL(line.getOptionValue("find")));
            } catch (MalformedURLException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            args.setImages(line.getArgs());
        }
    }
}
