/*
 * Copyright (c) 2012 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALEXANDER DOVZHIKOV ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL dovzhikov OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * BIntNode.java
 *
 * Created on 07.02.2012 17:30:05
 */

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
