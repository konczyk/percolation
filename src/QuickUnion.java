import java.util.Scanner;

class QuickUnion {

    private int[] nodes;
    private int[] sizes;
    private int count;

    public QuickUnion(int n) {
        nodes = new int[n];
        sizes = new int[n];
        count = n;
        for (int i = 0; i < n; i++) {
            nodes[i] = i;
            sizes[i] = 1;
        }
    }

    public int count() {
        return count;
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

        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        QuickUnion qu = new QuickUnion(n);
        while (sc.hasNext()) {
            int p = sc.nextInt();
            int q = sc.nextInt();
            if (!qu.connected(p, q)) {
                qu.union(p, q);
                System.out.println(p + " " + q);
            }
        }

        System.out.println("Nodes: " + n);
        System.out.println("Components: " + qu.count());
    }

}
