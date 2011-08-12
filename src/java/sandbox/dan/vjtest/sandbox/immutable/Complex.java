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
 * Complex.java
 *
 * Created on 12.08.2011 16:14:46
 */

package dan.vjtest.sandbox.immutable;

/**
 * @author Alexander Dovzhikov
 */
public final class Complex implements Cloneable {
    private final double real;
    private final double imaginary;

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public Complex add(Complex c) {
        return new Complex(real + c.real, imaginary + c.imaginary);
    }

    public Complex mult(Complex c) {
        return new Complex(real * c.real - imaginary * c.imaginary, real * c.imaginary + imaginary * c.real);
    }

    public double abs() {
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Complex complex = (Complex) o;

        return (Double.compare(real, complex.real) == 0 && Double.compare(imaginary, complex.imaginary) == 0);
    }

    @Override
    public int hashCode() {
        long re = (real != +0.0d) ? Double.doubleToLongBits(real) : 0L;
        long im = (imaginary != +0.0d) ? Double.doubleToLongBits(imaginary) : 0L;

        return 31 * (int)(re ^ (re >>> 32)) + (int) (im ^ (im >>> 32));
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // should never happen
            throw new InternalError();
        }
    }

    @Override
    public String toString() {
        return new StringBuilder("Complex{real=").append(real)
                .append(", imaginary=").append(imaginary).append('}').toString();
    }
}
