package dan.vjtest.sandbox.swing.genie;

import dan.vjtest.sandbox.swing.util.UIUtil;

import javax.swing.*;

/**
 * @author Alexander Dovzhikov
 */
public class GenieSandbox {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame("Genie Panel");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.getContentPane().add(new ImagePanel("0019.jpeg"));
                f.setSize(600, 450);
                UIUtil.centerComponent(f);
                f.setVisible(true);
            }
        });
    }
}
