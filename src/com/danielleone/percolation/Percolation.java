import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF uf;
    private final boolean[][] opened;
    private final int top;
    private final int size;
    private final int bottom;
    private int openCount;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n < 1) {
            throw new java.lang.IllegalArgumentException("n must be > 0");
        }
        size = n;
        bottom = size * size + 1;
        top = 0;
        opened = new boolean[size][size];
        openCount = 0;
        // +2 for the virtual top and bottom sites
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    private int getIndex(int row, int col) {
        checkReference(row, col);
        return size * (row - 1) + col;
    }
    
    private void checkReference(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IndexOutOfBoundsException();
        }    
    }

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        checkReference(row, col);
        if (!opened[row - 1][col - 1]) {
            opened[row - 1][col - 1] = true;
            ++openCount;
        }

        int i = getIndex(row, col);

        if (row == 1) {
            uf.union(i, top);
        }
        if (row == size) {
            uf.union(i, bottom);
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(i, getIndex(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            uf.union(i, getIndex(row, col + 1));
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(i, getIndex(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            uf.union(i, getIndex(row + 1, col));
        }
    }

    /**
     * is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        checkReference(row, col);
        return opened[row - 1][col - 1];
    }

    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        return uf.connected(top, getIndex(row, col));
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return openCount;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return uf.connected(top, bottom);
    }
}