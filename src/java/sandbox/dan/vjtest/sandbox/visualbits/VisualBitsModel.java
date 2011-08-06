package dan.vjtest.sandbox.visualbits;

import javax.swing.event.EventListenerList;

/**
 * @author Alexander Dovzhikov
 */
public class VisualBitsModel {
    private int value;

    private EventListenerList listeners = new EventListenerList();

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        int oldValue = this.value;

        if (value != oldValue) {
            this.value = value;
            fireModelChanged();
        }
    }

    public void shiftLeft(int bits) {
        setValue(getValue() << bits);
    }

    public void shiftRight(int bits) {
        setValue(getValue() >> bits);
    }

    public void addVisualBitsListener(VisualBitsListener listener) {
        listeners.add(VisualBitsListener.class, listener);
    }

    public void removeVisualBitsListener(VisualBitsListener listener) {
        listeners.remove(VisualBitsListener.class, listener);
    }

    protected void fireModelChanged() {
        for (VisualBitsListener listener : listeners.getListeners(VisualBitsListener.class)) {
            listener.modelChanged();
        }
    }
}
