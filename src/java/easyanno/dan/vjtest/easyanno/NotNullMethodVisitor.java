package dan.vjtest.easyanno;

import dan.vjtest.easyanno.util.AnnotationAdapter;
import org.objectweb.asm.*;

/**
 * @author Alexander Dovzhikov
 */
public class NotNullMethodVisitor extends MethodAdapter {
    private static final String NOTNULL_DESCRIPTOR = Type.getDescriptor(NotNull.class);

    private boolean notNull;
    private String errorMessage;

    public NotNullMethodVisitor(MethodVisitor mv) {
        super(mv);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        notNull |= NOTNULL_DESCRIPTOR.equals(desc);

        final AnnotationVisitor visitor = super.visitAnnotation(desc, visible);
        AnnotationVisitor result = visitor;

        if (result != null && notNull) {
            try {
                errorMessage = NotNull.class.getDeclaredMethod("value").getDefaultValue().toString();
            } catch (NoSuchMethodException e) {
                errorMessage = "Strange Message";
            }

            result = new AnnotationAdapter(visitor) {
                @Override
                public void visit(String name, Object value) {
                    if ("value".equals(name))
                        errorMessage = value.toString();

                    super.visit(name, value);
                }
            };
        }

        return result;
    }

    @Override
    public void visitInsn(int opcode) {
        if (notNull && opcode == Opcodes.ARETURN) {
            Label notNullLabel = new Label();

            mv.visitInsn(Opcodes.DUP);
            mv.visitJumpInsn(Opcodes.IFNONNULL, notNullLabel);
            mv.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalStateException");
            mv.visitInsn(Opcodes.DUP);
//            mv.visitLdcInsn("Not null constraint violated");
            mv.visitLdcInsn(errorMessage);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalStateException", "<init>", "(Ljava/lang/String;)V");
            mv.visitInsn(Opcodes.ATHROW);
            mv.visitLabel(notNullLabel);
        }

        super.visitInsn(opcode);
    }
}
