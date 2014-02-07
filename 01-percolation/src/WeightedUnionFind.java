public class WeightedUnionFind {
    public WeightedUnionFind(int n) {
        unions = new int[n];
        weights = new int[n];

        for (int i = 0; i < unions.length; ++i) {
            unions[i] = i;
        }
        for (int i = 0; i < weights.length; ++i) {
            weights[i] = 1;
        }
    }

    public boolean connected(int p, int q) {
        return rootOf(p) == rootOf(q);
    }

    public void union(int p, int q) {
        int pRoot = rootOf(p);
        int qRoot = rootOf(q);

        if (qRoot == pRoot) return;

        if (weights[pRoot] < weights[qRoot]) {
            unions[qRoot] = pRoot;
            weights[pRoot] += weights[qRoot];
        } else {
            unions[pRoot] = qRoot;
            weights[qRoot] += weights[pRoot];
        }
    }

    private int rootOf(int i) {
        while (unions[i] != i) {
            i = unions[i];
        }
        return i;
    }

    private final int[] unions;
    private final int[] weights;
}
