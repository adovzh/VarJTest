package dan.vjtest.sandbox.annotations.temp.greet;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Alexander Dovzhikov
 */
public abstract class ContextAwareMethodVisitor extends MethodAdapter {
    private final String name;
    private final String desc;

    public ContextAwareMethodVisitor(MethodVisitor mv, String name, String desc) {
        super(mv);

        this.name = name;
        this.desc = desc;
    }

    protected String getName() {
        return name;
    }

    protected String getDesc() {
        return desc;
    }
}
