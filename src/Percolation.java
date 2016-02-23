/*
 * Percolation data type as n x n grid
 */
public class Percolation {

    private static final int SITE_BLOCKED = 0;
    private static final int SITE_OPEN = 1;
    private static final int SITE_BOTTOM_CONNECTED = 2;

    // virtual sites
    private final int VIRT_TOP = 0;
    private final int VIRT_BOTTOM;

    private final QuickUnion qu;
    private final int[] grid;
    private final int gridWidth;
    private final int gridSize;

    // create n x n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }

        // grid includes two additional virtual sites
        // at indices 0 and n * n + 1
        gridWidth = n;
        gridSize = gridWidth * gridWidth + 2;
        grid = new int[gridSize];

        VIRT_BOTTOM = gridSize - 1;
        grid[VIRT_TOP] = grid[VIRT_BOTTOM] = SITE_OPEN;

        qu = new QuickUnion(gridSize);
    }

    public boolean percolates() {
        return qu.connected(VIRT_TOP, VIRT_BOTTOM);
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int site = coordsToIndex(row, col);
        grid[site] = SITE_OPEN;
        // bottom row
        if (row == gridWidth) {
            grid[site] |= SITE_BOTTOM_CONNECTED;
        }

        connectWithNeighbors(row, col);
    }

    private void connectWithNeighbors(int row, int col) {
        connectWithTopNeighbor(row, col);
        connectWithRightNeighbor(row, col);
        connectWithBottomNeighbor(row, col);
        connectWithLeftNeighbor(row, col);

        // connect with virtual bottom site after connecting neighbors
        final int siteRoot = qu.find(coordsToIndex(row, col));
        if (qu.connected(siteRoot, VIRT_TOP) &&
            (grid[siteRoot] & SITE_BOTTOM_CONNECTED) == SITE_BOTTOM_CONNECTED) {
            qu.union(siteRoot, VIRT_BOTTOM);
        }
    }

    private void connectWithTopNeighbor(int row, int col) {
        final int site = coordsToIndex(row, col);

        if (row == 1) {
            connect(site, VIRT_TOP);
        } else if (isOpen(row - 1, col)) {
            connect(site, coordsToIndex(row - 1, col));
        }
    }

    private void connectWithRightNeighbor(int row, int col) {
        if (col < gridWidth && isOpen(row, col + 1)) {
            connect(coordsToIndex(row, col), coordsToIndex(row, col + 1));
        }
    }

    private void connectWithBottomNeighbor(int row, int col) {
        if (row < gridWidth && isOpen(row + 1, col)) {
            connect(coordsToIndex(row, col), coordsToIndex(row + 1, col));
        }
    }

    private void connectWithLeftNeighbor(int row, int col) {
        if (col > 1 && isOpen(row, col - 1)) {
            connect(coordsToIndex(row, col), coordsToIndex(row, col - 1));
        }
    }

    private void connect(int site, int neighbor) {
        final int siteRoot = qu.find(site);
        final int siteRootStatus = grid[siteRoot];
        final int neighborRootStatus = grid[qu.find(neighbor)];

        qu.union(siteRoot, neighbor);
        grid[qu.find(site)] = siteRootStatus | neighborRootStatus;
    }

    public boolean isFull(int row, int col) {
        final int site = coordsToIndex(row, col);

        return isOpen(row, col) && qu.connected(site, VIRT_TOP);
    }

    public boolean isOpen(int row, int col) {
        return grid[coordsToIndex(row, col)] != SITE_BLOCKED;
    }

    private int coordsToIndex(int row, int col) {
        if (row < 1 || row > gridWidth) {
            throw new IllegalArgumentException("row must be between 1 and n");
        }

        if (col < 1 || col > gridWidth) {
            throw new IllegalArgumentException("col must be between 1 and n");
        }

        return (row - 1) * gridWidth + col;
    }

}
