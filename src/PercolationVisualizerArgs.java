import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

public class PercolationVisualizerArgs {

    private static final String GRID = "--grid-width";
    @Parameter(
        names = {GRID, "-gw"},
        description = "Grid width",
        validateWith = PositiveIntegerValidator.class)
    public Integer gridWidth;

    private static final String FRAC = "--fraction";
    @Parameter(
        names = {FRAC, "-f"},
        description = "Fraction of open sites (default 0.7)",
        validateWith = FractionValidator.class)
    public Double fraction = 0.7;

    @Parameter(
        names = {"--help", "-h"},
        description = "Usage help",
        help = true)
    public boolean help = false;

    private static final String STDIN = "--stdin";
    @Parameter(
        names = {STDIN, "-"},
        description = "Read data from stdin "
            + "(nodes first, followed by site coordinates)")
    public boolean stdin = false;

    public void validate() throws ParameterException {

        if (stdin && gridWidth != null) {
            throw new ParameterException(
                "Parameters " + STDIN + " and " + GRID + " are mutually exclusive");
        }
    }
}
