public class Percolation {
    private int N;
    private boolean[] sites;
    private WeightedQuickUnionUF percolatingSites;
    private WeightedQuickUnionUF fullSites;
    private int virtualTop;
    private int virtualBottom;

    public Percolation(int n) {
        N = n;
        sites = new boolean[N * N];
        virtualTop = sites.length;
        virtualBottom = virtualTop + 1;

        // needs two extra indices for virtual top and virtual bottom site
        percolatingSites = new WeightedQuickUnionUF(sites.length + 2);
        // needs extra index for virtual top
        fullSites = new WeightedQuickUnionUF(sites.length + 1);
    }

    public void open(int i, int j) {
        verifyIndex(i, j);
        int curIndex = getIndex(i, j);
        sites[curIndex] = true;

        if (i == 1) {
            percolatingSites.union(virtualTop, curIndex);
            fullSites.union(virtualTop, curIndex);
        }
        if (N == i) {
            percolatingSites.union(virtualBottom, curIndex);
        }

        for (Pair neighbour : neighbours(i, j)) {
            int n = getIndex(neighbour.getI(), neighbour.getJ());

            if (sites[n]) {
                percolatingSites.union(n, curIndex);
                fullSites.union(n, curIndex);
            }
        }
    }

    public boolean isOpen(int i, int j) {
        verifyIndex(i, j);
        return sites[getIndex(i, j)];
    }

    public boolean isFull(int i, int j) {
        verifyIndex(i, j);
        return fullSites.connected(virtualTop, getIndex(i, j));
    }

    public boolean percolates() {
        return percolatingSites.connected(virtualTop, virtualBottom);
    }

    private int getIndex(int i, int j) {
        return (i - 1) * N + (j - 1);
    }

    private void verifyIndex(int i, int j) {
        if (!validIndex(i, j)) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean validIndex(int i, int j) {
        return 1 <= i && i <= N && 1 <= j && j <= N;
    }

    private Pair[] neighbours(int i, int j) {
        Pair[] all = new Pair[4];
        all[0] = new Pair(i - 1, j);
        all[1] = new Pair(i + 1, j);
        all[2] = new Pair(i, j - 1);
        all[3] = new Pair(i, j + 1);

        boolean[] validIndices = new boolean [4];
        int numValid = 0;
        for (int pi = 0; pi < all.length; ++pi) {
            if (validIndex(all[pi].getI(), all[pi].getJ())) {
                validIndices[pi] = true;
                numValid++;
            }
        }

        Pair[] valid = new Pair[numValid];
        int ind = 0;
        for (int vi = 0; vi < validIndices.length; ++vi) {
            if (validIndices[vi]) {
                valid[ind] = all[vi];
                ++ind;
            }
        }

        return valid;
    }

    private class Pair {
        private int i;
        private int j;

        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public int getI() {
            return i;
        }

        public int getJ() {
            return j;
        }
    }
}
