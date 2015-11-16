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
        return 0;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return 0;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return 0;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return 0;
    }

    public static void main(String[] args) {
    }

}
