package dan.vjtest.sandbox.visualbits;

import dan.vjtest.sandbox.swing.util.UIUtil;

import javax.swing.*;

/**
 * @author Alexander Dovzhikov
 */
public class VisualBitsApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Visual Bits");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(new VisualBitsGadget());
                frame.setSize(300, 200);
                UIUtil.centerComponent(frame);
                frame.setVisible(true);
            }
        });
    }
}
