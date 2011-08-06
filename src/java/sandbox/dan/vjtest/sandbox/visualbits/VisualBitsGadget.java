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


        controls.add(new JButton(new AbstractAction("SHL") {
            public void actionPerformed(ActionEvent e) {
                model.shiftLeft(1);
            }
        }));

        controls.add(new JButton(new AbstractAction("SHR") {
            public void actionPerformed(ActionEvent e) {
                model.shiftRight(1);
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
