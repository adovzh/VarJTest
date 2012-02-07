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
 * ImagePanel.java
 *
 * Created on 07.02.2012 17:29:28
 */

package dan.vjtest.sandbox.swing.genie;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * @author Alexander Dovzhikov
 */
public class ImagePanel extends JPanel implements AnimationTracker {
    private final ImageIcon imageIcon;
    private final Dimension pref;
    private final Genie genie;
    private final Animation animation = new Animation();
    private boolean dirty = true;
    Image img;

    public ImagePanel(String path) {
        this.imageIcon = new ImageIcon(getClass().getResource(path));
        this.pref = new Dimension(imageIcon.getIconWidth(), imageIcon.getIconHeight());
        this.genie = new Genie(0.2, 0.3);
        genie.setSourceImage(createBufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight()));
        animationAdvanced(0);
        animation.setListener(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                dirty = true;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (dirty) {
                    Dimension d = getSize();
                    genie.setSourceImage(createBufferedImage(d.width, d.height));
                }

                animation.animate();
            }
        });

        setOpaque(false);
    }

    private BufferedImage createBufferedImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
//        paintComponent(g);
//        imageIcon.paintIcon(this, g, 0, 0);
        g.drawImage(imageIcon.getImage(), 0, 0, width, height, this);
        g.dispose();

        return image;
    }

    @Override
    public Dimension getPreferredSize() {
        return pref;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Dimension d = getSize();

//        System.out.println("Draw image");
        g.drawImage(img, 0, 0, d.width, d.height, this);
    }

    public void animationAdvanced(double p) {
        img = genie.getImage(p);
        repaint();
    }
}
