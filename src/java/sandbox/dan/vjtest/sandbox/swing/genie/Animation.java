package dan.vjtest.sandbox.swing.genie;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alexander Dovzhikov
 */
public class Animation implements ActionListener {
    private final Timer timer;
    private AnimationTracker listener = null;

    private double state;
    private double step;

    public Animation() {
        state = 0;
        step = -0.05;

        timer = new Timer(50, this);
    }

    public void animate() {
        step = -step;

        if (!timer.isRunning())
            timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (step > 0 && state + step > 1 || step < 0 && state < 0) {
            timer.stop();
        } else {
            state += step;
            fireAnimationAdvanced();
        }
    }

    public void setListener(AnimationTracker listener) {
        this.listener = listener;
    }

    private void fireAnimationAdvanced() {
        if (listener != null) {
            listener.animationAdvanced(state);
        }
    }
}
