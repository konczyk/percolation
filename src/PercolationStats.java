import java.util.Random;

public class PercolationStats {

    // number of percolation tests to run
    private final int runs;
    // percolation grid width (same as height)
    private final int gridWidth;

    private Random rand = new Random();

    private double[] results;

    public PercolationStats(int gridWidth, int runs) {
        if (gridWidth <= 0 || runs <= 0) {
            throw new IllegalArgumentException(
                "grid width and runs must be greater than 0");
        }
        this.gridWidth = gridWidth;
        this.runs = runs;
    }

    // compute percolation results
    public double[] computeResults() {
        if (results != null) {
            return results;
        }
        final int sites= gridWidth * gridWidth;
        results = new double[runs];

        for (int i = 0; i < runs; i++) {
            Percolation p = new Percolation(gridWidth);
            int open = 0;
            while (!p.percolates()) {
                randomOpen(p);
                open++;
            }
            results[i] = (double)open / sites;
        }

        return results;
    }

    private void randomOpen(Percolation p) {
        while (true) {
            int row = 1 + rand.nextInt(gridWidth);
            int col = 1 + rand.nextInt(gridWidth);
            if (!p.isOpen(row, col)) {
                p.open(row, col);
                break;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double[] res = computeResults();

        double sum = 0;
        for (double result: res) {
            sum += result;
        }

        return sum / res.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double[] res = computeResults();

        double mean = mean();
        double sum = 0;
        for (double result: res) {
            sum += (result - mean) * (result - mean);
        }

        return Math.sqrt(sum / (res.length - 1));
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(runs);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(runs);
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
