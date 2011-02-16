package dan.vjtest.sandbox.swing.paint;

import dan.vjtest.sandbox.swing.util.UIUtil;

import javax.swing.*;
import java.awt.*;

/**
 * @author Alexander Dovzhikov
 */
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JPanel panel = new JPanel(new BorderLayout());
                panel.add(new GradientPanel());
                JFrame f = new JFrame("App");
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.getContentPane().add(panel);
                f.setSize(400, 300);
                UIUtil.centerComponent(f);
                f.setVisible(true);
            }
        });
    }
}
