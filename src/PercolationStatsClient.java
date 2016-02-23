import com.beust.jcommander.*;

public class PercolationStatsClient {

    @Parameter(
        names={"--grid-width", "-gw"},
        description = "Grid width",
        required = true,
        validateWith = PositiveIntegerValidator.class)
    private int gridWidth;

    @Parameter(
        names = {"--trials", "-t"},
        description = "Number of trials",
        required = true,
        validateWith = PositiveIntegerValidator.class)
    private int trials;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    public static void main(String[] args) {
        PercolationStatsClient client = new PercolationStatsClient();
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
        PercolationStats stats = new PercolationStats(gridWidth, trials);
        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo()
            + ", " + stats.confidenceHi());
    }
}
