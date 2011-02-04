package dan.vjtest.sandbox.annotations.temp.greet;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @author Alexander Dovzhikov
 */
public class ByeMethodVisitor extends ContextAwareMethodVisitor {
    private static final String BYE_DESCRIPTOR = Type.getDescriptor(Bye.class);

    private boolean bye;

    public ByeMethodVisitor(MethodVisitor mv, String name, String desc) {
        super(mv, name, desc);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        bye |= BYE_DESCRIPTOR.equals(desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitInsn(int opcode) {
        if (bye && opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN || opcode == Opcodes.ATHROW) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
            mv.visitLdcInsn("Goodbye, " + getName());
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V");
        }

        super.visitInsn(opcode);
    }
}
