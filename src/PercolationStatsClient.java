import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class PercolationStatsClient {

    @Parameter(
        names = {"--grid-width", "-gw"},
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
        jc.setProgramName("PercolationStats");
        try {
            jc.parse(args);
            if (client.help || args.length == 0) {
                jc.usage();
                return;
            }
            client.run();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
        }
    }

    private void run() {
        PercolationStats stats = new PercolationStats(gridWidth, trials);
        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo()
            + ", " + stats.confidenceHi());
    }
}
