import com.beust.jcommander.*;

public class PercolationStatsClient {

    @Parameter(
        names={"--grid-width", "-gw"},
        description = "Grid width",
        required = true,
        validateWith = PositiveInteger.class)
    private int gridWidth;

    @Parameter(
        names = {"--trials", "-t"},
        description = "Number of trials",
        required = true,
        validateWith = PositiveInteger.class)
    private int trials;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    private boolean help = false;

    public static class PositiveInteger implements IParameterValidator {
        @Override
        public void validate(String name, String value)
            throws ParameterException {

            String msg = "Parameter " + name + " should be a positive integer"
                         + " (found " + value + ")";
            try {
                int n = Integer.parseInt(value);
                if (n < 0)
                    throw new ParameterException(msg);
            } catch (Exception e) {
                throw new ParameterException(msg);
            }
        }
    }

    public static void main(String[] args){
        PercolationStatsClient launcher = new PercolationStatsClient();
        JCommander jc = new JCommander(launcher);
        try {
            jc.parse(args);
            if (launcher.help) {
                jc.usage();
                return;
            }
            launcher.run();
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
