package cap;

import cap.ArgumentParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;

public class CommandLineInterpreter {

    private ArgumentParser parser = new ArgumentParser();

    public CommandLineInterpreter() {

    }

    public CommandLineInterpreter(ArgumentParser parser) {
        this.parser = parser;
    }

    public void run(String[] arguments) {
        try {
            this.interpret(this.parser.parse(arguments));
        } catch (ParseException exception) {
            this.parser.printHelp();
        }
    }

    protected void interpret(CommandLine line) {
        if (line.hasOption("help")) {
            this.parser.printHelp();
            return;
        }

        if (line.hasOption("captcha-system")) {
            System.out.println(line.getOptionValue("captcha-system"));
        }

        if (line.hasOption("server-mode")) {
            return;
        } else if (line.hasOption("find")) {
            System.out.println(line.getOptionValue("find"));
            return;
        }

        for (String imagePath : line.getArgs()) {
            System.out.println(imagePath);
        }
    }
}
