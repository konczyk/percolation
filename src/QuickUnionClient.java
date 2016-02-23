import com.beust.jcommander.*;
import java.util.Random;
import java.util.Scanner;

public class QuickUnionClient {

    private Random rand = new Random();

    public static void main(String[] args) {
        QuickUnionArgs quickUnionArgs = new QuickUnionArgs();
        JCommander jc = new JCommander(quickUnionArgs);
        try {
            jc.parse(args);
            quickUnionArgs.validate();
            if (quickUnionArgs.help || args.length == 0) {
                jc.usage();
                return;
            }
            QuickUnionClient client = new QuickUnionClient();
            if (!quickUnionArgs.stdin) {
                client.runRandom(quickUnionArgs.nodes, quickUnionArgs.connections);
            } else {
                client.runStdIn();
            }
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    public void runRandom(int nodes, int connections) {
        QuickUnion qu = new QuickUnion(nodes);

        for (int i = 0; i < connections; ++i) {
            int p = rand.nextInt(nodes);
            int q = rand.nextInt(nodes);
            if (!qu.connected(p, q)) {
                qu.union(p, q);
                System.out.println(p + " " + q);
            }
        }

        System.out.println("Nodes: " + nodes);
        System.out.println("Components: " + qu.count());
    }

    public void runStdIn() {
        int nodes = 0;
        int components = 0;
        try (Scanner scanner = new Scanner(System.in)) {
            nodes = scanner.nextInt();
            QuickUnion qu = new QuickUnion(nodes);
            while (scanner.hasNext()) {
                int p = scanner.nextInt();
                int q = scanner.nextInt();
                if (!qu.connected(p, q)) {
                    qu.union(p, q);
                    System.out.println(p + " " + q);
                }
            }
            components = qu.count();
        }

        System.out.println("Nodes: " + nodes);
        System.out.println("Components: " + components);
    }

    private static class QuickUnionArgs {

        private static final String NODES = "--nodes";
        @Parameter(
            names = {NODES, "-n"},
            description = "Number of nodes",
            validateWith = PositiveIntegerValidator.class)
        private Integer nodes;

        private static final String CONN = "--connections";
        @Parameter(
            names = {CONN, "-c"},
            description = "Number of random connections",
            validateWith = PositiveIntegerValidator.class)
        private Integer connections;

        @Parameter(
            names = {"--help", "-h"},
            description = "Usage help",
            help = true)
        private boolean help = false;

        private static final String STDIN = "--stdin";
        @Parameter(
            names = {STDIN, "-"},
            description = "Read data from stdin "
                + "(nodes first, followed by connection pairs)")
        private boolean stdin = false;

        public void validate() throws ParameterException {

            if (stdin && nodes != null) {
                throw new ParameterException(
                    "Parameters " + STDIN + " and " + NODES + " are mutually exclusive");
            } else if (stdin && connections != null) {
                throw new ParameterException(
                    "Parameters " + STDIN + " and " + CONN + " are mutually exclusive");
            } else if (nodes != null && connections == null) {
                throw new ParameterException(
                    "Parameter " + CONN + " is required whenever " + NODES + " is used");
            } else if (nodes == null && connections != null) {
                throw new ParameterException(
                    "Parameter " + NODES + " is required whenever " + CONN + " is used");
            }
        }

    }

}
