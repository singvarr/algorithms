import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private int openSites;
    private final WeightedQuickUnionUF trackPercolatesWQF;
    private final WeightedQuickUnionUF trackFullWQF;
    private final int bottomI;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                "Should be initialized with positive integer"
            );
        }

        grid = new int[n][n];
        bottomI = n * n + 1;
        trackFullWQF = new WeightedQuickUnionUF(bottomI);
        trackPercolatesWQF = new WeightedQuickUnionUF(bottomI + 1);
    }

    private boolean areCoordsValid(int row, int col) {
        if (row < 0 || col < 0 || grid.length <= row) return false;

        return grid[row].length > col;
    }

    private int mapTo1DArray(int row, int col) {
        return row * grid.length + col + 1;
    }

    private void connectSites(int firstI, int secRow, int secCol) {
        if (!areCoordsValid(secRow, secCol)) return;

        int secondI = mapTo1DArray(secRow, secCol);

        if (grid[secRow][secCol] != 0) {
            trackFullWQF.union(firstI, secondI);
            trackPercolatesWQF.union(firstI, secondI);
        }
    }

    private void checkCoords(int row, int col) {
        if (!areCoordsValid(row, col)) {
            throw new IllegalArgumentException("Incorrect coords of grid");
        }
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        int i = mapTo1DArray(row - 1, col - 1);

        openSites++;
        grid[row - 1][col - 1] = i;

        connectSites(i, row - 2, col - 1);
        connectSites(i, row - 1, col - 2);
        connectSites(i, row - 1, col);
        connectSites(i, row, col - 1);

        if (i <= grid.length) {
            trackFullWQF.union(i, 0);
            trackPercolatesWQF.union(i, 0);
        }

        if (i >= (bottomI - grid.length) && i < bottomI) {
            trackPercolatesWQF.union(i, bottomI);
        }
    }

    public boolean isFull(int row, int col) {
        checkCoords(row - 1, col - 1);
        int index = mapTo1DArray(row - 1, col - 1);

        return trackFullWQF.connected(0, index);
    }

    public boolean isOpen(int row, int col) {
        checkCoords(row - 1, col - 1);

        return grid[row - 1][col - 1] != 0;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return trackPercolatesWQF.connected(0, bottomI);
    }
}
