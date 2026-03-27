package opencode.examples.plainjava.testing;

import java.util.Arrays;
import java.util.List;

public class ArgumentParser {

    public TestConfiguration parse(String[] args) {
        TestConfiguration config = new TestConfiguration();

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "--baseUrl":
                    if (i + 1 < args.length) {
                        config.setBaseUrl(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--baseUrl requires a value");
                    }
                    break;

                case "--username":
                    if (i + 1 < args.length) {
                        config.setUsername(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--username requires a value");
                    }
                    break;

                case "--password":
                    if (i + 1 < args.length) {
                        config.setPassword(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--password requires a value");
                    }
                    break;

                case "--provider":
                    if (i + 1 < args.length) {
                        config.setProvider(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--provider requires a value");
                    }
                    break;

                case "--model":
                    if (i + 1 < args.length) {
                        config.setModel(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--model requires a value");
                    }
                    break;

                case "--providerApiKey":
                    if (i + 1 < args.length) {
                        config.setProviderApiKey(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--providerApiKey requires a value");
                    }
                    break;

                case "--examples":
                    if (i + 1 < args.length) {
                        String examplesStr = args[++i];
                        List<String> examples = Arrays.asList(examplesStr.split(","));
                        config.setExampleNames(examples);
                        config.setRunAll(false);
                    } else {
                        throw new IllegalArgumentException("--examples requires a value");
                    }
                    break;

                case "--all":
                    config.setRunAll(true);
                    config.getExampleNames().clear();
                    break;

                case "--noColor":
                    config.setColorOutput(false);
                    break;

                case "--logFile":
                    if (i + 1 < args.length) {
                        config.setLogFile(args[++i]);
                    } else {
                        throw new IllegalArgumentException("--logFile requires a value");
                    }
                    break;

                case "--help":
                case "-h":
                    printUsage();
                    System.exit(0);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown argument: " + arg);
            }
        }

        validateArguments(config);
        return config;
    }

    public void printUsage() {
        System.out.println("OpenCode Java SDK Test Runner");
        System.out.println("==============================");
        System.out.println();
        System.out.println("Usage: java -jar opencode-examples-plain-java.jar [OPTIONS]");
        System.out.println();
        System.out.println("Options:");
        System.out.println("  --baseUrl <url>          OpenCode server URL (default: http://localhost:4096)");
        System.out.println("  --username <user>        Authentication username (default: opencode)");
        System.out.println("  --password <pass>        Authentication password (default: opencode123)");
        System.out.println("  --provider <name>        LLM provider (default: Z.AI)");
        System.out.println("  --model <name>           LLM model (default: GLM-4.7)");
        System.out.println("  --providerApiKey <key>   Provider API key (optional, loaded from env if not provided)");
        System.out.println("  --examples <name1,name2> Specific examples to run (comma-separated)");
        System.out.println("  --all                    Run all examples (default)");
        System.out.println("  --noColor                Disable colored output");
        System.out.println("  --logFile <path>         Write detailed log to file");
        System.out.println("  --help, -h               Display this help message");
        System.out.println();
        System.out.println("Available Examples:");
        System.out.println("  SystemInfo, Configuration, Provider, Project, FileOperations,");
        System.out.println("  SessionCrud, SessionAdvanced, Message, DevTools, Experimental,");
        System.out.println("  Instance, Interactive, Mcp, Todo, Vcs, EventStreaming, Pty");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("  # Run all examples with default settings");
        System.out.println("  java -jar opencode-examples-plain-java.jar");
        System.out.println();
        System.out.println("  # Run specific examples");
        System.out.println("  java -jar opencode-examples-plain-java.jar --examples SystemInfo,SessionCrud");
        System.out.println();
        System.out.println("  # Run with custom server and credentials");
        System.out.println("  java -jar opencode-examples-plain-java.jar --baseUrl http://myserver:4096 --username admin --password secret");
        System.out.println();
        System.out.println("  # Run with custom LLM provider");
        System.out.println("  java -jar opencode-examples-plain-java.jar --provider OpenAI --model gpt-4");
    }

    private void validateArguments(TestConfiguration config) {
        try {
            config.validate();
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println();
            printUsage();
            System.exit(1);
        }
    }
}
