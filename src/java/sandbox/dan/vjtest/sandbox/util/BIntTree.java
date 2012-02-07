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
 * BIntTree.java
 *
 * Created on 07.02.2012 17:30:05
 */

package dan.vjtest.sandbox.util;

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
        if (root.checkFull()) {
            BIntNode newRoot = new BIntNode(order);
            root.split(newRoot);
            root = newRoot;
        }
    }

    public static void main(String[] args) {
        // todo: replace with unit test
        BIntTree tree = new BIntTree(6);

        for (int i = 1; i <= 20; i++) {
            tree.insert(i);
            tree.insert(21 - i);
        }

        tree.root.debugPrint();
    }
}
