package dan.vjtest.sandbox.annotations;

import org.objectweb.asm.*;

/**
 * @author Alexander Dovzhikov
 */
public class NotNullMethodVisitor extends MethodAdapter {
    private static final String NOTNULL_DESCRIPTOR = Type.getDescriptor(NotNull.class);

    private boolean notNull;

    public NotNullMethodVisitor(MethodVisitor mv) {
        super(mv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        notNull |= NOTNULL_DESCRIPTOR.equals(desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitInsn(int opcode) {
        if (notNull && opcode == Opcodes.ARETURN) {
            Label notNullLabel = new Label();

            mv.visitInsn(Opcodes.DUP);
            mv.visitJumpInsn(Opcodes.IFNONNULL, notNullLabel);
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalStateException");
            mv.visitInsn(Opcodes.DUP);
            mv.visitLdcInsn("Not null constraint violated");
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalStateException", "<init>", "(Ljava/lang/String;)V");
            mv.visitInsn(Opcodes.ATHROW);
            mv.visitLabel(notNullLabel);
        }

        super.visitInsn(opcode);
    }
}
