/*
 * Copyright (c) 2011 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
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
 * ComplexTest.java
 *
 * Created on 12.08.2011 16:34:39
 */

package dan.vjtest.sandbox.immutable;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Dovzhikov
 */
public class ComplexTest {
    private static final double EPS = 1e-5;

    @Test
    public void cloning() {
        Complex c = new Complex(1, 1);
        Complex c2 = (Complex) c.clone();

        Assert.assertEquals(c, c2);
        Assert.assertEquals(new Complex(c.getReal(), c.getImaginary()), c);
    }

    @Test
    public void product() {
        Complex c = new Complex(30, -5);
        Complex c2 = new Complex(-2, 4);
        Complex product = c.mult(c2);
        Complex product2 = c2.mult(c);

        checkProduct(c, c2, product);
        checkProduct(c, c2, product2);
        Assert.assertEquals(product, product2);
    }

    private void checkProduct(Complex c, Complex c2, Complex product) {
        Assert.assertEquals(c.getReal() * c2.getReal() - c.getImaginary() * c2.getImaginary(), product.getReal(), EPS);
        Assert.assertEquals(c.getReal() * c2.getImaginary() + c.getImaginary() * c2.getReal(), product.getImaginary(), EPS);
    }
}
