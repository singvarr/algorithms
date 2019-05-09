import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONF_INTERVAL = 1.96;
    private final int trialsCount;
    private final double[] fractions;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("invalid args");
        }

        trialsCount = trials;
        fractions = new double[trials];

        for (int i = 0; i < trialsCount; i++) {
            Percolation perc = new Percolation(n);
            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                perc.open(row, col);
            }
            fractions[i] = perc.numberOfOpenSites() / Math.pow(n, 2);
        }
    }

    public double mean() {
        return StdStats.mean(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - CONF_INTERVAL * stddev() / Math.sqrt(trialsCount);
    }

    public double confidenceHi() {
        return mean() + CONF_INTERVAL * stddev() / Math.sqrt(trialsCount);
    }

    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(size, trials);

        StdOut.println("mean = " + percStats.mean());
        StdOut.println("stddev = " + percStats.stddev());
        StdOut.println(
                "95% confidence interval = [" + percStats.confidenceLo()
                        + ", " + percStats.confidenceHi() + "]");
    }
}
