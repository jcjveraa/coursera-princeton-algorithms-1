import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] outcomes;
    private final int n;

    // perform independent trials on an n-by-n grid
    public PercolationStats(final int n, int trials) {
        this.n = n;
        outcomes = new double[trials];

        for (int i = 0; i < trials; i++) {
            int percolationTreshold = 0;
            var percolator = new Percolation(n);
            while (!percolator.percolates()) {
                percolationTreshold++;
                openCell(percolator);
            }

            this.outcomes[i] = (1.0d * percolationTreshold) / (n * n);
        }
    }

    private void openCell(Percolation p) {
        RowCol rowCol;
        do {
            var idx = StdRandom.uniformInt(this.n * this.n);
            rowCol = this.indexToRowCol(this.n, idx);
        } while (p.isOpen(rowCol.row, rowCol.col));

        p.open(rowCol.row, rowCol.col);
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(outcomes);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(outcomes);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - confInterval();
    }

    private double confInterval() {
        return 1.96 * stddev() / Math.sqrt(this.outcomes.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + confInterval();
    }

    // test client (see below)
    public static void main(String[] args) {
        var pstats = new PercolationStats(200, 100);
        System.out.println(pstats.mean());
        System.out.println(pstats.stddev());
        System.out.println(pstats.confidenceLo());
        System.out.println(pstats.confidenceHi());
    }

    private RowCol indexToRowCol(int n, int i) {
        RowCol rowCol = new RowCol();
        rowCol.row = i / n + 1; // 1-indexed
        rowCol.col = i % n + 1;
        return rowCol;
    }

    private static class RowCol {
        public int row;
        public int col;
    }
}
