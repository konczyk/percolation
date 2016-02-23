import com.beust.jcommander.*;
import java.util.Random;

public class QuickUnionClient {

    @Parameter(
        names={"--nodes", "-n"},
        description = "Number of nodes",
        required = true,
        validateWith = PositiveIntegerValidator.class)
    private int nodes;

    @Parameter(
        names = {"--connections", "-c"},
        description = "Number of random connections",
        required = true,
        validateWith = PositiveIntegerValidator.class)
    private int connections;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    private Random rand = new Random();

    public static void main(String[] args) {
        QuickUnionClient client = new QuickUnionClient();
        JCommander jc = new JCommander(client);
        try {
            jc.parse(args);
            if (client.help) {
                jc.usage();
                return;
            }
            client.run();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    public void run() {
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
}
