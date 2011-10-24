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
 * ProgressReporter.java
 *
 * Created on 21.10.2011 19:18:30
 */

package dan.vjtest.sandbox.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

/**
* @author Alexander Dovzhikov
*/
public class ProgressReporter implements Reporter {
    private static final Logger log = LoggerFactory.getLogger(ProgressReporter.class);

    private JProgressBar progress;
    private JLabel statusLabel;
    private AtomicInteger updateCount;
    private JPanel content;

    public ProgressReporter(final String name) {
        this.updateCount = new AtomicInteger(0);

        Runnable initializer = new Runnable() {
            public void run() {
                statusLabel = new JLabel(" ");
                progress = new JProgressBar();
                progress.setStringPainted(true);
                progress.setValue(0);
                progress.setIndeterminate(true);

                content = new JPanel(new BorderLayout());
                content.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                content.add(progress, BorderLayout.CENTER);
                content.add(new JLabel(name + ":"), BorderLayout.NORTH);
                content.add(statusLabel, BorderLayout.SOUTH);
            }
        };

        if (SwingUtilities.isEventDispatchThread()) {
            initializer.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(initializer);
            } catch (InterruptedException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public void setLength(final int length) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                progress.setMaximum(length);
                progress.setIndeterminate(false);
            }
        });
    }

    @Override
    public void updateProgress(final String message) {
        if (updateCount.getAndIncrement() == 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int value = progress.getValue();
                    progress.setValue(value + updateCount.getAndSet(0));
                    statusLabel.setText(message);
                }
            });
        }
    }

    public Component getContent() {
        return content;
    }
}
