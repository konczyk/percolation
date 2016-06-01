import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class QuickUnionConfig {

    private static final String NODES = "--nodes";
    private static final String CONN = "--connections";
    private static final String STDIN = "--stdin";

    @Parameter(
        names = {NODES, "-n"},
        description = "Number of nodes",
        validateWith = PositiveIntegerValidator.class)
    private int nodes;

    @Parameter(
        names = {CONN, "-c"},
        description = "Number of random connections",
        validateWith = PositiveIntegerValidator.class)
    private int connections;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    @Parameter(
        names = {STDIN, "-"},
        description = "Read data from stdin "
            + "(nodes first, followed by connection pairs)")
    private boolean stdin = false;

    public int getNodes() {
        return nodes;
    }

    public int getConnections() {
        return connections;
    }

    public boolean isStdin() {
        return stdin;
    }

    public static QuickUnionConfig parseConfig(String[] args) {
        QuickUnionConfig config = new QuickUnionConfig();
        JCommander jc = new JCommander(config);
        jc.setProgramName("QuickUnionClient");
        try {
            jc.parse(args);
            config.validate();
            if (config.help || args.length == 0) {
                jc.usage();
                System.exit(0);
            }
        } catch (ParameterException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return config;
    }

    private void validate() {

        if (stdin && nodes > 0) {
            throw new ParameterException(
                "Parameters " + STDIN + " and " + NODES +
                " are mutually exclusive");
        } else if (stdin && connections > 0) {
            throw new ParameterException(
                "Parameters " + STDIN + " and " + CONN +
                " are mutually exclusive");
        } else if (nodes > 0 && connections == 0) {
            throw new ParameterException(
                "Parameter " + CONN + " is required whenever " +
                NODES + " is used");
        } else if (nodes == 0 && connections > 0) {
            throw new ParameterException(
                "Parameter " + NODES + " is required whenever " +
                CONN + " is used");
        }
    }
}
