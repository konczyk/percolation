import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class PercolationStatsConfig {

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

    public int getGridWidth() {
        return gridWidth;
    }

    public int getTrials() {
        return trials;
    }

    public static PercolationStatsConfig parseConfig(String[] args) {
        PercolationStatsConfig config = new PercolationStatsConfig();
        JCommander jc = new JCommander(config);
        jc.setProgramName("PercolationStats");
        if (args.length == 0) {
            jc.usage();
            System.exit(0);
        }

        try {
            jc.parse(args);
            if (config.help) {
                jc.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        return config;
    }

    public void run() {
        PercolationStats stats = new PercolationStats(gridWidth, trials);
        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo()
            + ", " + stats.confidenceHi());
    }
}
