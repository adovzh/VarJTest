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
 * VisualBitsGadget.java
 *
 * Created on 07.02.2012 17:31:59
 */

package dan.vjtest.sandbox.visualbits;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author Alexander Dovzhikov
 */
public class VisualBitsGadget extends JPanel {
    private final VisualBitsModel model;

    public VisualBitsGadget() {
        super(new GridLayout(2, 1));
        model = new VisualBitsModel();

        JPanel controls = new JPanel();
        final JTextField tf = new JTextField(10);


        controls.add(new JButton(new AbstractAction("<<") {
            public void actionPerformed(ActionEvent e) {
                model.shiftLeft(1);
            }
        }));

        controls.add(new JButton(new AbstractAction(">>") {
            public void actionPerformed(ActionEvent e) {
                model.shiftRight(1);
            }
        }));

        controls.add(new JButton(new AbstractAction(">>>") {
            public void actionPerformed(ActionEvent e) {
                model.unsignedRightShift(1);
            }
        }));

        controls.add(tf);

        controls.add(new JButton(new AbstractAction("Set") {
            public void actionPerformed(ActionEvent e) {
                model.setValue(Integer.parseInt(tf.getText()));
            }
        }));

        add(new VisualBits(model));
        add(controls);
    }
}
