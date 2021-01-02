import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final int size;
    private int numberOfOpenSites;
    private final WeightedQuickUnionUF percolateUnion; // used to check percolates
    private final WeightedQuickUnionUF fullUnion;  // used to check isFull

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("n <= 0 for the Percolation constructor");

        numberOfOpenSites = 0;
        size = n;
        // the last 2 elements are a virtual top site and a virtual bottom site
        percolateUnion = new WeightedQuickUnionUF(n * n + 2);
        // the last element is a virtual top site
        fullUnion = new WeightedQuickUnionUF(n * n + 1);
        grid = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
                if (i == 0) {
                    percolateUnion.union(n * n, j);
                    fullUnion.union(n * n, j);
                }
                else if (i == n - 1) {
                    percolateUnion.union(n * n + 1, i * n + j);
                }
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException(
                    "row or col must be in the range of [1, n] in open()");

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            numberOfOpenSites++;
            if (row > 1 && isOpen(row - 1, col)) {
                percolateUnion.union(mapIndex(row, col), mapIndex(row - 1, col));
                fullUnion.union(mapIndex(row, col), mapIndex(row - 1, col));
            }
            if (row < size && isOpen(row + 1, col)) {
                percolateUnion.union(mapIndex(row, col), mapIndex(row + 1, col));
                fullUnion.union(mapIndex(row, col), mapIndex(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                percolateUnion.union(mapIndex(row, col), mapIndex(row, col - 1));
                fullUnion.union(mapIndex(row, col), mapIndex(row, col - 1));
            }
            if (col < size && isOpen(row, col + 1)) {
                percolateUnion.union(mapIndex(row, col), mapIndex(row, col + 1));
                fullUnion.union(mapIndex(row, col), mapIndex(row, col + 1));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size) {
            throw new IllegalArgumentException(
                    "row or col must be in the range of [1, n] in isOpen()");
        }

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > size || col < 1 || col > size)
            throw new IllegalArgumentException(
                    "row or col must be in the range of [1, n] in isFull()");

        return isOpen(row, col) && (fullUnion.find(mapIndex(row, col))
                == fullUnion.find(size * size));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (size == 1)
            return isOpen(size, size);
        return (percolateUnion.find(size * size) == percolateUnion.find(size * size + 1));
    }

    // maps 2D indices to 1D
    private int mapIndex(int row, int col) {
        return (row - 1) * size + col - 1;
    }
}
