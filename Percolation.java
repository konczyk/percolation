/*
 * Percolation data type as n x n grid
 */
public class Percolation {

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }
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

    public static void main(String[] args) {
    }

}
