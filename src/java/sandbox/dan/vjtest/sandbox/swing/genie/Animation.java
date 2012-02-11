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
 * Animation.java
 *
 * Created on 07.02.2012 17:29:28
 */

package dan.vjtest.sandbox.swing.genie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexander Dovzhikov
 */
public class Animation implements ActionListener {
    private final Timer timer;
    private AnimationTracker listener = null;

    private double state;
    private double step;

    public Animation() {
        state = 0;
        step = -0.05;

        timer = new Timer(100, this);
    }

    public void animate() {
        step = -step;

        if (!timer.isRunning())
            timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (step > 0 && state + step > 1 || step < 0 && state < 0) {
            timer.stop();
        } else {
            state += step;
            fireAnimationAdvanced();
        }
    }

    public void setListener(AnimationTracker listener) {
        this.listener = listener;
    }

    private void fireAnimationAdvanced() {
        if (listener != null) {
            listener.animationAdvanced(state);
        }
    }
}
