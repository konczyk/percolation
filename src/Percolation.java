/**
 * Percolation data type as n x n grid
 */
public class Percolation {

    private static final byte SITE_BLOCKED = 0;
    private static final byte SITE_OPEN = 1;
    private static final byte SITE_BOTTOM_CONNECTED = 2;

    // virtual sites
    private final int virtualTop;
    private final int virtualBottom;

    private final QuickUnion quickUnion;
    private final byte[] statusGrid;
    private final int gridWidth;

    // create n x n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }

        // grid includes two additional virtual sites
        // at indices 0 and n * n + 1
        gridWidth = n;
        int gridSize = gridWidth * gridWidth + 2;
        virtualTop = 0;
        virtualBottom = gridSize - 1;

        statusGrid = new byte[gridSize];
        statusGrid[virtualTop] = SITE_OPEN;
        statusGrid[virtualBottom] = SITE_OPEN;
        quickUnion = new QuickUnion(gridSize);
    }

    public boolean percolates() {
        return quickUnion.connected(virtualTop, virtualBottom);
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }

        int site = coordsToIndex(row, col);
        statusGrid[site] = SITE_OPEN;
        // bottom row
        if (row == gridWidth) {
            statusGrid[site] |= SITE_BOTTOM_CONNECTED;
        }

        connectWithNeighbors(row, col);
    }

    private void connectWithNeighbors(int row, int col) {
        connectWithTopNeighbor(row, col);
        connectWithRightNeighbor(row, col);
        connectWithBottomNeighbor(row, col);
        connectWithLeftNeighbor(row, col);

        // connect with virtual bottom site after connecting neighbors
        int siteRoot = quickUnion.find(coordsToIndex(row, col));
        int bottomConnected = statusGrid[siteRoot] & SITE_BOTTOM_CONNECTED;
        if (quickUnion.connected(siteRoot, virtualTop) &&
                bottomConnected == SITE_BOTTOM_CONNECTED) {
            quickUnion.union(siteRoot, virtualBottom);
        }
    }

    private void connectWithTopNeighbor(int row, int col) {
        final int site = coordsToIndex(row, col);

        if (row == 1) {
            connect(site, virtualTop);
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
        int siteRoot = quickUnion.find(site);
        byte siteRootStatus = statusGrid[siteRoot];
        byte neighborRootStatus = statusGrid[quickUnion.find(neighbor)];

        quickUnion.union(siteRoot, neighbor);
        statusGrid[quickUnion.find(site)] =
            (byte) (siteRootStatus | neighborRootStatus);
    }

    public boolean isFull(int row, int col) {
        final int site = coordsToIndex(row, col);

        return isOpen(row, col) && quickUnion.connected(site, virtualTop);
    }

    public boolean isOpen(int row, int col) {
        return statusGrid[coordsToIndex(row, col)] != SITE_BLOCKED;
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
