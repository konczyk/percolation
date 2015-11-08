/*
 * Percolation data type as n x n grid
 */
public class Percolation {

    // site status, default is blocked
    private static final int BLOCKED = 0;
    private static final int OPEN = 1;

    // indices of virtual sites
    private final int VIRT_TOP = 0;
    private final int VIRT_BOTTOM;

    // connectivity data structure
    private final QuickUnion qu;
    // number of all sites in the grid
    private final int[] grid;
    // grid width (same as height)
    private final int gridWidth;
    // total number of grid sites
    private final int gridSize;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }

        gridWidth = n;
        // grid includes two additional virtual sites
        // at indices 0 and n * n + 1
        gridSize = gridWidth * gridWidth + 2;
        grid = new int[gridSize];
        VIRT_BOTTOM = gridSize - 1;
        // virtual sites are open by default
        grid[VIRT_TOP] = OPEN;
        grid[VIRT_BOTTOM] = OPEN;

        // initialize QuickUnion data structure
        qu = new QuickUnion(gridSize);
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        // open the site
        grid[translateCoords(row, col)] = OPEN;

        // connect neighbors
        connectNeighbors(row, col);
    }

    private void connectNeighbors(int row, int col) {
        final int site = translateCoords(row, col);
        int neighbor;

        // connect to the top neighbor, check for virtual site
        if (row == 1 || isOpen(row - 1, col)) {
            neighbor = row == 1 ? VIRT_TOP
                                : translateCoords(row - 1, col);
            qu.union(site, neighbor);
        }

        // connect to the bottom neighbor, check for virtual site
        if (row == gridWidth || isOpen(row + 1, col)) {
            neighbor = row == gridWidth ? VIRT_BOTTOM
                                        : translateCoords(row + 1, col);
            qu.union(site, neighbor);
        }

        // connect to the right neighbor
        if (col < gridWidth && isOpen(row, col + 1)) {
            qu.union(site, translateCoords(row, col + 1));
        }

        // connect to the left neighbor
        if (col > 1 && isOpen(row, col - 1)) {
            qu.union(site, translateCoords(row, col - 1));
        }
    }

    public boolean isOpen(int row, int col) {
        return grid[translateCoords(row, col)] == OPEN;
    }

    // check if the given site is connected with the virtual sites
    public boolean isFull(int row, int col) {
        final int site = translateCoords(row, col);

        return isOpen(row, col) &&
               qu.connected(site, VIRT_TOP) &&
               qu.connected(site, VIRT_BOTTOM);
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
