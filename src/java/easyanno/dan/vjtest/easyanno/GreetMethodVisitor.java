package dan.vjtest.easyanno;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Alexander Dovzhikov
 */
public class GreetMethodVisitor extends ContextAwareMethodVisitor {
    private static final String GREET_DESCRIPTOR = Type.getDescriptor(Greet.class);

    private boolean greet;

    public GreetMethodVisitor(MethodVisitor mv, String name, String desc) {
        super(mv, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        greet |= GREET_DESCRIPTOR.equals(desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        super.visitCode();

        if (greet) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Hello, " + getName());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        }
    }
}
