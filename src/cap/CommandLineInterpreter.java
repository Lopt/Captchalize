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

    public boolean run(String[] arguments) {
        try {
            CommandLine line = this.parser.parse(arguments);
            if (line.hasOption("help")) {
                this.parser.printHelp();
                return false;
            }

            this.interpret(line);

        } catch (ParseException exception) {
            this.parser.printHelp();
            return false;

        } catch (CaptchalizeParseException exception) {
            this.parser.printHelp();
            return false;
        }

        return true;
    }

    protected void createDefaultOptions() {
        Option serverMode = OptionBuilder
            .withLongOpt("server-mode")
            .withDescription("running in server mode.")
            .create("s");

        Option serverPort = OptionBuilder
            .withLongOpt("server-port")
            .withDescription("port of the hosted http-server. (default 80)")
            .hasArg()
            .withArgName("port")
            .create("p");

        Option serverMaxConnections = OptionBuilder
            .withLongOpt("max-server-connects")
            .withDescription("maximum connections of the hosted http-server. (default 30)")
            .hasArg()
            .withArgName("num")
            .create();

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

        Option dbName = OptionBuilder
            .withLongOpt("database")
            .withDescription("name of the database. hsql and sqlite they are path + name (default db/captchalize.db)")
            .hasArg()
            .withArgName("name")
            .create("db");

        Option dbType = OptionBuilder
            .withLongOpt("database-type")
            .withDescription("type of the database. hsql, sqlite, mysql (default hsql)")
            .hasArg()
            .withArgName("dbtype")
            .create("dt");

        Option dbServer = OptionBuilder
            .withLongOpt("database-server")
            .withDescription("host of the database. only needed on mysql database-type.")
            .hasArg()
            .withArgName("host")
            .create("ds");

        Option dbPort = OptionBuilder
            .withLongOpt("database-port")
            .withDescription("port of the database. only needed on mysql database-type. (default 3306)")
            .hasArg()
            .withArgName("port")
            .create("dp");

        Option dbUser = OptionBuilder
            .withLongOpt("database-user")
            .withDescription("user of the database. (default on hsql 'SA')")
            .hasArg()
            .withArgName("name")
            .create("du");

        Option dbPassword = OptionBuilder
            .withLongOpt("database-password")
            .withDescription("password for the database user.")
            .hasArg()
            .withArgName("pw")
            .create("dp");

        Option captchaSystem = OptionBuilder
            .withLongOpt("captcha-system")
            .withDescription("use a specific CAPTCHA system.")
            .hasArg()
            .withArgName("system_name")
            .create("sys");

        Option debugMode = OptionBuilder
            .withLongOpt("debug")
            .withDescription("print step by step results.")
            .create("d");

        Option debugGUIMode = OptionBuilder
            .withLongOpt("debug-gui")
            .withDescription("offer a gui for step by step results.")
            .create("gui");

        Option help = OptionBuilder
            .withLongOpt("help")
            .withDescription("print this message.")
            .create();

        Options op = this.parser.getOptions();

        op.addOption(serverMode);
        op.addOption(serverPort);
        op.addOption(serverMaxConnections);
        op.addOption(maxImageSize);
        op.addOption(maxRequestSize);

        op.addOption(captchaSystem);
        op.addOption(parseMode);

        op.addOption(debugMode);
        op.addOption(debugGUIMode);

        op.addOption(dbName);
        op.addOption(dbType);
        op.addOption(dbServer);
        op.addOption(dbPort);
        op.addOption(dbUser);
        op.addOption(dbPassword);

        op.addOption(help);

        this.parser.setOptions(op);
    }

    protected void interpret(CommandLine line) throws CaptchalizeParseException {
        RunArguments args = RunArguments.getInstance();

        args.setDebugMode(line.hasOption("debug"));
        args.setDebugGui(line.hasOption("debug-gui"));

        if (line.hasOption("captcha-system")) {
            args.setCaptchaSystem(UnknownCaptchaSystem.findSystem(line.getOptionValue("captcha-system")));
        }

        if (line.hasOption("database-type")) {
            args.setDatabaseType(line.getOptionValue("database-type"));
        }
        if (line.hasOption("database")) {
            args.setDatabaseName(line.getOptionValue("database"));
        }
        if (line.hasOption("database-server")) {
            args.setDatabaseServerName(line.getOptionValue("database-server"));
        }
        if (line.hasOption("database-port")) {
            args.setDatabaseServerPort(Integer.parseInt(line.getOptionValue("database-port")));
        }
        if (line.hasOption("database-user")) {
            args.setDatabaseUser(line.getOptionValue("database-user"));
        }
        if (line.hasOption("database-password")) {
            args.setDatabasePassword(line.getOptionValue("database-password"));
        }

        if (line.hasOption("server-mode")) {
            args.setServerMode(true);

            if (line.hasOption("server-port")) {
                args.setServerPort(Integer.parseInt(line.getOptionValue("server-port")));
            }
            if (line.hasOption("max-server-connections")) {
                args.setMaxServerConnections(Integer.parseInt(line.getOptionValue("max-server-connections")));
            }
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
