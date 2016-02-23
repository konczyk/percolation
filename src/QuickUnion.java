class QuickUnion {

    private int[] nodes;
    private int[] sizes;
    private int components;

    public QuickUnion(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be larger than 0");
        }

        nodes = new int[n];
        sizes = new int[n];
        components = n;
        for (int i = 0; i < n; ++i) {
            nodes[i] = i;
            sizes[i] = 1;
        }
    }

    public int count() {
        return components;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // merge components containing nodes p and q
    // smaller component gets merged into larger
    public void union(int p, int q) {
        int proot = find(p);
        int qroot = find(q);

        if (proot == qroot) {
            return;
        }

        if (sizes[proot] < sizes[qroot]) {
            nodes[proot] = qroot;
            sizes[qroot] += sizes[proot];
        } else {
            nodes[qroot] = proot;
            sizes[proot] += sizes[qroot];
        }

        components--;
    }

    // return the root node of the component containing node p
    public int find(int p) {
        validate(p);
        while (p != nodes[p]) {
            p = nodes[p];
        }

        return p;
    }

    private void validate(int p) {
        if (p < 0 || p >= nodes.length) {
            throw new IndexOutOfBoundsException("Invalid node: " + p);
        }
    }

}
