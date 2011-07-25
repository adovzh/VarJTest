package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class BIntNode {
    private final int order;
    private final int[] keys;
    private final BIntNode[] nodes;

    private int size;
    private BIntNode parent;

    public BIntNode(int order) {
        this.order = order;
        this.keys = new int[order];
        this.nodes = new BIntNode[order + 1];
        this.size = 0;
        this.parent = null;
    }

    private BIntNode(int order, BIntNode node, int from, int length) {
        this(order);

        System.arraycopy(node.keys, from, keys, 0, length);
        System.arraycopy(node.nodes, from, nodes, 0, length + 1);

        for (int i = 0; i <= length; i++)
            if (nodes[i] != null && nodes[i].parent == node)
                nodes[i].parent = this;

        size = length;
    }

    public void add(int key) {
        int i = 0; // position to insert

        while (i < size) {
            if (key == keys[i])
                return;
            if (key < keys[i])
                break;
            i++;
        }

        // insert in position i = [0..size]
        BIntNode child = nodes[i];

        if (child == null) {
            System.arraycopy(keys, i, keys, i + 1, size - i);
            keys[i] = key;
            size++;
        } else {
            child.add(key);
        }

        if (checkFull() && parent != null) {
            split(parent);
        }
    }

    public void split(BIntNode parent) {
        int medianIndex = order / 2;
        BIntNode left = new BIntNode(order, this, 0, medianIndex);
        BIntNode right = new BIntNode(order, this, medianIndex + 1, order - medianIndex - 1);

        parent.insert(keys[medianIndex], left, right);
    }

    private void insert(int key, BIntNode left, BIntNode right) {
        int index = 0;

        while (index < size) {
            if (key < keys[index])
                break;
            index++;
        }

        left.parent = this;
        right.parent = this;

        System.arraycopy(keys, index, keys, index + 1, size - index);
        keys[index] = key;
        System.arraycopy(nodes, index + 1, nodes, index + 2, size - index);
        nodes[index] = left;
        nodes[index + 1] = right;
        size++;
    }

    public boolean checkFull() {
        return (size >= order);
    }

    public void debugPrint() {
        for (int i = 0; i < size; i++) {
            if (nodes[i] != null) nodes[i].debugPrint();
            System.out.print(" " + keys[i]);
        }

        if (nodes[size] != null) nodes[size].debugPrint();
    }
}
