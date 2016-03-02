import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class PercolationVisualizerConfig {

    private static final String GRID = "--grid-width";
    private static final String FRACTION = "--fraction";
    private static final String STDIN = "--stdin";

    @Parameter(
        names = {GRID, "-gw"},
        description = "Grid width",
        validateWith = PositiveIntegerValidator.class)
    private int gridWidth;

    @Parameter(
        names = {FRACTION, "-f"},
        description = "Fraction of open sites",
        validateWith = FractionValidator.class)
    private double fraction = 0.7;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    @Parameter(
        names = {STDIN, "-"},
        description = "Read data from stdin "
                    + "(nodes first, followed by site coordinates)")
    private boolean stdin = false;

    public int getGridWidth() {
        return gridWidth;
    }

    public double getFraction() {
        return fraction;
    }

    public boolean isStdin() {
        return stdin;
    }

    public static PercolationVisualizerConfig parseConfig(String[] args) {
        PercolationVisualizerConfig config = new PercolationVisualizerConfig();
        JCommander jc = new JCommander(config);
        jc.setProgramName("PercolationVisualizer");
        if (args.length == 0) {
            jc.usage();
            System.exit(0);
        }

        try {
            jc.parse(args);
            config.validate();
        } catch (ParameterException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }

        if (config.help) {
            jc.usage();
            System.exit(0);
        }

        return config;
    }

    private void validate() {
        if (stdin && gridWidth > 0) {
            throw new IllegalArgumentException(
                "Parameters " + STDIN + " and " + GRID
                + " are mutually exclusive");
        }
    }
}
