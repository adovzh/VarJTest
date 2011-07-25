package dan.vjtest.sandbox.util.tmpext;

/**
 * @author Alexander Dovzhikov
 */
public class BIntTree {
    private final int order;
    private BIntNode root = null;

    public BIntTree(int order) {
        this.order = order;
    }

    public void insert(int key) {
        if (root == null)
            root = new BIntNode(order);

        root.add(key);

        // todo: code duplication detected
        if (root.checkSize()) {
            BIntNode newRoot = new BIntNode(order);
            root.split(newRoot);
            root = newRoot;
        }
    }

    public static void main(String[] args) {
        // todo: replace with unit test
        BIntTree tree = new BIntTree(5);

        for (int i = 1; i <= 20; i++) {
            tree.insert(i);
            tree.insert(21 - i);
        }

        tree.root.debugPrint();
    }
}
