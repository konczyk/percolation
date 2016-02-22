import java.util.Random;

public class PercolationStats {

    private final int runs;
    private final int gridWidth;

    private Random rand = new Random();

    private double[] percolationThresholds;

    public PercolationStats(int gridWidth, int runs) {
        if (gridWidth <= 0 || runs <= 0) {
            throw new IllegalArgumentException(
                "grid width and runs must be greater than 0");
        }
        this.gridWidth = gridWidth;
        this.runs = runs;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(runs);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(runs);
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

        percolationThresholds = new double[runs];
        final int sites = gridWidth * gridWidth;

        for (int i = 0; i < runs; i++) {
            Percolation p = new Percolation(gridWidth);
            int open = 0;
            while (!p.percolates()) {
                openRandomSite(p);
                open++;
            }
            percolationThresholds[i] = (double)open / sites;
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

    public static void main(String[] args) {
        int grid = Integer.parseInt(args[0]);
        int runs = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(grid, runs);

        System.out.println("mean\t\t\t= " + stats.mean());
        System.out.println("stddev\t\t\t= " + stats.stddev());
        System.out.println("95% confidence interval = " + stats.confidenceLo()
                            + ", " + stats.confidenceHi());
    }

}
