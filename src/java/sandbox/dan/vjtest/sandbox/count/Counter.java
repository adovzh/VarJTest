package dan.vjtest.sandbox.count;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexander Dovzhikov
 */
public class Counter extends JLabel implements ActionListener {
    private final Timer timer;
    private int value;

    public Counter() {
        this(0);
    }

    public Counter(int value) {
        this.value = value;

        setHorizontalAlignment(CENTER);
        setFont(new Font(Font.SERIF, Font.BOLD, 24));
        updateValue();

        timer = new Timer(1000, this);
    }

    private void updateValue() {
        setText(String.valueOf(value));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (timer.isRunning()) {
            ++value;
            updateValue();
        }
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        value = 0;
        updateValue();
    }
}
