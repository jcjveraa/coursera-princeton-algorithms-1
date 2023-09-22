import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private final int n;
    private int numberOfOpenSites = 0;
    private final int topVirtualNode;
    private final int bottomVirtualNode;
    private final WeightedQuickUnionUF qu;
    private final int[] openClosed;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        int gridSize = n * n + 2; // +2 for virtual nodes
        this.openClosed = new int[gridSize];
        this.topVirtualNode = gridSize - 2;
        this.bottomVirtualNode = gridSize - 1;

        qu = new WeightedQuickUnionUF(gridSize);
        for (int i = 0; i < n; i++) {
            var j = rowColToIndex(1, i + 1);
            qu.union(j, this.topVirtualNode);

            var k = rowColToIndex(n, i + 1);
            qu.union(k, this.bottomVirtualNode);
        }
    }


    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        var i = this.rowColToIndex(row, col);
        this.openClosed[i] = 1;
        this.numberOfOpenSites++;

        if (row > 1) {
            var right = row - 1;
            unionIfOpen(right, col, i);
        }

        if (row < n) {
            var left = row + 1;
            unionIfOpen(left, col, i);
        }

        if (col > 1) {
            var above = col - 1;
            unionIfOpen(row, above, i);
        }

        if (col < n) {
            var below = col + 1;
            unionIfOpen(row, below, i);
        }
    }

    private void unionIfOpen(int row, int above, int i) {
        if (isOpen(row, above)) {
            int neighborIndex = this.rowColToIndex(row, above);
            this.qu.union(neighborIndex, i);
        }
    }


    public boolean isOpen(int row, int col) {
        return this.openClosed[this.rowColToIndex(row, col)] > 0;
    }

    public boolean isFull(int row, int col) {
        var i = this.rowColToIndex(row, col);

        return isOpen(row, col) && qu.find(i) == qu.find(this.topVirtualNode);
    }

    public int numberOfOpenSites() {
        return this.numberOfOpenSites;
    }

    public boolean percolates() {
        return qu.find(this.bottomVirtualNode) == qu.find(this.topVirtualNode);
    }

    private int rowColToIndex(int row, int col) {
        checkBounds(row);
        checkBounds(col);
        row--; // 1-indexed
        col--;
        return this.n * row + col;
    }

    private void checkBounds(int rowOrCol){
        if(rowOrCol < 1 || rowOrCol > n) {
            throw new IllegalArgumentException();
        }
    }


}
