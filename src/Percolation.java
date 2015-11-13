/*
 * Percolation data type as n x n grid
 */
public class Percolation {

    // site status, default is blocked
    private static final int BLOCKED = 0;
    private static final int OPEN = 1;
    // indicate if a site is open and connected to bottom row
    private static final int CONN_BOTTOM = 2;

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

        // initialize QuickUnion data structure
        qu = new QuickUnion(gridSize);
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        // open the site
        int site = translateCoords(row, col);
        grid[site] = OPEN;
        // special flags for bottom sites
        if (row == gridWidth) {
            grid[site] |= CONN_BOTTOM;
        }

        // connect neighbors
        connectNeighbors(row, col);
    }

    private void connectNeighbors(int row, int col) {
        final int site = translateCoords(row, col);

        // connect to the top neighbor, skip virtual site
        if (row == 1 || isOpen(row - 1, col)) {
            connect(site, row == 1 ? VIRT_TOP : translateCoords(row - 1, col));
        }

        // connect to the bottom neighbor, skip virtual site
        if (row < gridWidth && isOpen(row + 1, col)) {
            connect(site, translateCoords(row + 1, col));
        }

        // connect to the right neighbor
        if (col < gridWidth && isOpen(row, col + 1)) {
            connect(site, translateCoords(row, col + 1));
        }

        // connect to the left neighbor
        if (col > 1 && isOpen(row, col - 1)) {
            connect(site, translateCoords(row, col - 1));
        }

        // union with virtual bottom if top is connected
        final int root = qu.find(site);
        if (qu.connected(root, VIRT_TOP) &&
            (grid[root] & CONN_BOTTOM) == CONN_BOTTOM)
        {
            qu.union(root, VIRT_BOTTOM);
        }

    }

    private void connect(int site, int neighbor) {
        final int siteRootStatus = grid[qu.find(site)];
        final int neighborRootStatus = grid[qu.find(neighbor)];
        qu.union(site, neighbor);
        // update site's root status after union
        grid[qu.find(site)] = siteRootStatus | neighborRootStatus;
    }

    public boolean isOpen(int row, int col) {
        return grid[translateCoords(row, col)] != BLOCKED;
    }

    // check if the given site is connected with the virtual top site
    public boolean isFull(int row, int col) {
        final int site = translateCoords(row, col);

        return isOpen(row, col) && qu.connected(site, VIRT_TOP);
    }

    // check percolation by examining connectivity of the virtual sites
    public boolean percolates() {
        return qu.connected(VIRT_TOP, VIRT_BOTTOM);
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
