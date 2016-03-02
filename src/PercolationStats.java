import java.util.Random;

public class PercolationStats {

    private final int gridWidth;
    private final int trials;

    private final Random rand = new Random();

    private double[] percolationThresholds;

    public PercolationStats(int gridWidth, int trials) {
        if (gridWidth <= 0 || trials <= 0) {
            throw new IllegalArgumentException(
                "grid width and trials must be greater than 0");
        }
        this.gridWidth = gridWidth;
        this.trials = trials;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        double[] thresholds = collectPercolationThresholds();

        double sum = 0;
        for (double threshold: thresholds) {
            sum += threshold;
        }

        return sum / thresholds.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (trials == 1) {
            return Double.NaN;
        }

        double[] thresholds = collectPercolationThresholds();

        double mean = mean();
        double sum = 0;
        for (double threshold: thresholds) {
            sum += (threshold - mean) * (threshold - mean);
        }

        return Math.sqrt(sum / (thresholds.length - 1));
    }

    protected double[] collectPercolationThresholds() {
        if (percolationThresholds != null) {
            return percolationThresholds;
        }

        percolationThresholds = new double[trials];
        final int sites = gridWidth * gridWidth;

        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(gridWidth);
            int open = 0;
            while (!p.percolates()) {
                openRandomSite(p);
                open++;
            }
            percolationThresholds[i] = (double) open / sites;
        }

        return percolationThresholds;
    }

    private void openRandomSite(Percolation p) {
        while (true) {
            int row = 1 + rand.nextInt(gridWidth);
            int col = 1 + rand.nextInt(gridWidth);
            if (!p.isOpen(row, col)) {
                p.open(row, col);
                break;
            }
        }
    }

}
