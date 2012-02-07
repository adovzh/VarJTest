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
 * ArrayQueue.java
 *
 * Created on 07.02.2012 17:30:29
 */

package dan.vjtest.sandbox.util;

/**
 * @author Alexander Dovzhikov
 */
public class ArrayQueue<T> implements Queue<T> {
    private final T[] data;
    private int head;
    private int tail;

    public ArrayQueue(int capacity) {
        data = (T[]) new Object[capacity + 1];
        head = tail = 0;
    }

    @Override
    public void enqueue(T elem) {
        if (isFull())
            throw new IllegalStateException("overflow");

        putElement(elem);
    }

    @Override
    public T dequeue() {
        if (isEmpty())
            throw new IllegalStateException("underflow");

        return getElement();
    }

    private boolean isFull() {
        return tail + 1 == head || head == 0 && tail == data.length - 1;
    }

    private boolean isEmpty() {
        return head == tail;
    }

    private void putElement(T elem) {
        data[tail++] = elem;

        if (tail == data.length)
            tail = 0;
    }

    private T getElement() {
        T element = data[head++];

        if (head == data.length)
            head = 0;

        return element;
    }
}
