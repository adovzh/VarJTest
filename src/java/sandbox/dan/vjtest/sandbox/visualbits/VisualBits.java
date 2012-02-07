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
 * VisualBits.java
 *
 * Created on 07.02.2012 17:31:59
 */

package dan.vjtest.sandbox.visualbits;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Dovzhikov
 */
public class VisualBits extends JPanel {
    private final VisualBitsModel model;

    public VisualBits(VisualBitsModel model) {
        this.model = model;
        model.addVisualBitsListener(new VisualBitsListener() {
            public void modelChanged() {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        String binaryString = toBinaryString(model.getValue());
        g.drawString(binaryString, 10, 10);
    }

    private static String toBinaryString(int value) {
        StringBuilder sb = new StringBuilder(40);
        int i;

        for (i = 31; i >= 0; i--) {
            sb.append(((value & (1 << i)) != 0) ? '1' : '0');

            if (i != 0 && (i & 3) == 0) {
                sb.append(' ');
            }
        }

        return sb.toString();
    }
}
