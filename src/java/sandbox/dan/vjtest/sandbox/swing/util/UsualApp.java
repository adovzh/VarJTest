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
 * UsualApp.java
 *
 * Created on 05.10.2011 19:10:57
 */

package dan.vjtest.sandbox.swing.util;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Dovzhikov
 */
public class UsualApp {
    private static final String DEFAULT_TITLE = "App";
    private static final Dimension DEFAULT_SIZE = new Dimension(400, 300);

    private String title;
    private Dimension size;
    private Component content;
    private JFrame frame;

    public UsualApp() {
    }

    public UsualApp(String title) {
        this.title = title;
    }

    public String getTitle() {
        return (title != null) ? title : DEFAULT_TITLE;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Dimension getSize() {
        return (size != null) ? size : DEFAULT_SIZE;
    }

    public void setSize(Dimension size) {
        this.size = size;
    }

    public void setContent(Component content) {
        this.content = content;
    }

    public void start() {
        if (content == null)
            throw new IllegalStateException("No content");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame = new JFrame(getTitle());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(content);
                frame.setSize(getSize());
                UIUtil.centerComponent(frame);
                frame.setVisible(true);
            }
        });
    }
}
