package week1;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation percolation;

    private double[] trials;
    private double mean;
    private double stddev;

    public PercolationStats(int n, int trials) {
        mean = -1;
        stddev = -1;

        this.trials = new double[trials];
        for (int i = 0; i < trials; i++) {
           this.trials[i] = calcThreshold(n);
        }
    }

    private double calcThreshold(int n) {
        int all = n * n;
        int openPoints = 0;
        percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n+1);
            int col = StdRandom.uniform(1, n+1);
            if (!percolation.isOpen(row, col)) {
                openPoints += 1;
                percolation.open(row, col);
            }
        }
        return 1.0 * openPoints / all;

    }

    public double mean() {
        if (mean == -1) {
            mean = StdStats.mean(trials);
        }
        return mean;
    }

    public double stddev() {
        if (stddev == -1) {
            stddev = StdStats.stddev(trials);
        }

        return stddev;
    }

    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials.length);
    }

    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials.length);
    }

    public static void main(String[] args) {
        int trials = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);

        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException();
        }

        PercolationStats stats = new PercolationStats(n, trials);
        System.out.println(String.format("mean = %f", stats.mean()));
        System.out.println(String.format("stddev  = %f", stats.stddev()));
        System.out.println(String.format("95%% confidence interval  = %f, %f",
                stats.confidenceLo(), stats.confidenceHi()));
    }
}
