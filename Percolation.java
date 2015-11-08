/*
 * Percolation data type as n x n grid
 */
public class Percolation {

    // site status, default is open
    public static final int OPEN = 0;
    public static final int BLOCKED = 1;

    // number of all sites in the grid
    private int[] grid;
    // grid width (same as height)
    private int gridWidth;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }

        gridWidth = n;
        // grid includes two additional virtual sites
        // at indices 0 and n * n + 1
        grid = new int[gridWidth * gridWidth + 2];
    }

    public void open(int i, int j) {
    }

    public boolean isOpen(int i, int j) {
        return false;
    }

    public boolean isFull(int i, int j) {
        return false;
    }

    public boolean percolates() {
        return false;
    }

    // translate coordinates to grid array indices
    // first index is 1
    private int translateCoords(int row, int col) {
        if (row < 1 || row > gridWidth) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }
        if (col < 1 || col > gridWidth) {
            throw new IllegalArgumentException("col must be between 1 and n");
        }

        return (row - 1) * gridWidth + col;
    }

    public static void main(String[] args) {
        // test coordinates
        Percolation p = new Percolation(10);
        System.out.println("Grid width: 10");
        System.out.println("(1, 1) => 1 ? " +
                            (p.translateCoords(1, 1) == 1));
        System.out.println("(4, 4) => 34 ? " +
                            (p.translateCoords(4, 4) == 34));
        System.out.println("(10, 10) => 100 ? " +
                            (p.translateCoords(10, 10) == 100));
    }

}
