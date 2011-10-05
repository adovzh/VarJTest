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
 * TimerTest.java
 *
 * Created on 05.10.2011 19:21:10
 */

package dan.vjtest.sandbox.swing.timer;

import dan.vjtest.sandbox.swing.util.UsualApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexander Dovzhikov
 */
public class TimerTest extends JPanel implements ActionListener, Runnable {
    private final JLabel label = new JLabel();
    private int count;

    TimerTest() {
        add(label);
//        new Timer(1000, this).start();
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this, 1, 1, TimeUnit.SECONDS);
    }

    public void actionPerformed(ActionEvent e) {
        count++;
        label.setText("Count: " + count);
    }

    public static void main(String[] args) {
        UsualApp app = new UsualApp("Timer test");
        app.setContent(new TimerTest());
        app.start();
    }

    public void run() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                actionPerformed(null);
            }
        });
    }
}
