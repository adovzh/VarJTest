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
 * Collisions.java
 *
 * Created on 07.02.2012 17:26:38
 */

package dan.vjtest.sandbox.multimethods;

/**
 * @author Alexander Dovzhikov
 */
public class Collisions {

    private Collisions() {
    }

    public static void collide(Asteroid a1, Asteroid a2) {
        System.out.printf("Collision: Asteroid %d with Asteroid %d%n", a1.hashCode(), a2.hashCode());
    }

    public static void collide(Asteroid a, Spaceship s) {
        System.out.printf("Collision: Asteroid %d with Spaceship %d%n", a.hashCode(), s.hashCode());
    }

    public static void collide(Spaceship s, Asteroid a) {
        System.out.printf("Collision: Spaceship %d with Asteroid %d%n", s.hashCode(), a.hashCode());
    }

    public static void collide(Spaceship s1, Spaceship s2) {
        System.out.printf("Collision: Spaceship %d with Spaceship %d%n", s1.hashCode(), s2.hashCode());
    }
}
