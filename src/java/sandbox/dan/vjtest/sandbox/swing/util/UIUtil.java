package dan.vjtest.sandbox.swing.util;

import java.awt.*;

/**
 * @author Alexander Dovzhikov
 */
public class UIUtil {

    private UIUtil() {
    }

    public static void centerComponent(Component c) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = c.getSize();

        c.setLocation((screen.width - d.width) / 2, (screen.height - d.height) / 2);
    }
}
