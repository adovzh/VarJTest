package dan.vjtest.sandbox.count;

import dan.vjtest.sandbox.swing.util.UIUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexander Dovzhikov
 */
public class App {
    private int counter = 0;
    private final JPanel content;

    private App() {
        final Counter counter = new Counter();

        // top
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter.start();
            }
        });

        JButton stopButton = new JButton("Stop");
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter.stop();
            }
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter.reset();
            }
        });

        JPanel top = new JPanel();
        top.add(startButton);
        top.add(stopButton);
        top.add(resetButton);

        content = new JPanel(new BorderLayout());
        content.add(top, BorderLayout.NORTH);
        content.add(counter, BorderLayout.CENTER);
    }

    public Component getContent() {
        return content;
    }

    public static void main(String ... args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App app = new App();
                JFrame f = new JFrame("Count");
                f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                f.getContentPane().add(app.getContent());
                f.setSize(250, 100);
                UIUtil.centerComponent(f);
                f.setVisible(true);
            }
        });
    }
}
