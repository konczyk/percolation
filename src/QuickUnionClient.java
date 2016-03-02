import java.util.Random;
import java.util.Scanner;

public class QuickUnionClient {

    private final Random rand = new Random();

    private void runFromRandom(int nodes, int connections) {
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

    private void runFromStdin() {
        int nodes;
        int components;
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


    public static void main(String[] args) {
        QuickUnionConfig config = QuickUnionConfig.parseConfig(args);
        QuickUnionClient client = new QuickUnionClient();

        if (config.isStdin()) {
            client.runFromStdin();
        } else {
            client.runFromRandom(config.getNodes(), config.getConnections());
        }
    }


}
