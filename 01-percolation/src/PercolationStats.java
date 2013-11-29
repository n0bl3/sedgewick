public class PercolationStats {
    private int N;
    private int T;
    private double[] openedFractions;

    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this.T = T;

        openedFractions = new double[T];

        runTest();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openedFractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openedFractions);
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * Math.sqrt(stddev()) / Math.sqrt(T);
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * Math.sqrt(stddev()) / Math.sqrt(T);
    }

    // test client, described below
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.printf("Need 2 arguments: N and T");
            return;
        }

        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percolationStats = new PercolationStats(N, T);

        StdOut.printf("mean = %d", percolationStats.mean());
        StdOut.printf("stddev = %d", percolationStats.stddev());
        StdOut.printf("95%% confidence interval = %d, %d",
                percolationStats.confidenceLo(),
                percolationStats.confidenceHi());
    }

    // thread unsafe!!!
    private void runTest() {
        double total = (double) N * N;
        for (int t = 0; t < T; ++t) {
            int opened = 0;
            Pair[] randomIndices = randomPairs(N);
            Percolation percolation = new Percolation(N);

            while (!percolation.percolates()) {
                try {
                    int i = randomIndices[opened].getI() + 1;
                    int j = randomIndices[opened].getJ() + 1;
                    percolation.open(i, j);
                    ++opened;
                } catch (ArrayIndexOutOfBoundsException e) {
                    StringBuilder indicesMessage = new StringBuilder();
                    for (Pair index : randomIndices) {
                        indicesMessage.append(index.toString());
                        indicesMessage.append("%n");
                    }

                    throw new ArrayIndexOutOfBoundsException(indicesMessage.toString());
                }
            }

            openedFractions[t] = opened / total;
        }
    }

    private Pair[] randomPairs(int size) {
        Pair[] pairs = new Pair[size * size];
        for (int i = 0; i < pairs.length; ++i) {
            pairs[i] = new Pair(i / size, i % size);
        }
        // random swap
        for (int i = 0; i < 10 * pairs.length; ++i) {
            int first = StdRandom.uniform(pairs.length);
            int second = StdRandom.uniform(pairs.length);
            Pair tmp = pairs[second];
            pairs[second] = pairs[first];
            pairs[first] = tmp;
        }
        return pairs;
    }

    private class Pair {
        private int i;
        private int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public String toString() {
            return String.format("(%d, %d)", i, j);
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }
}
