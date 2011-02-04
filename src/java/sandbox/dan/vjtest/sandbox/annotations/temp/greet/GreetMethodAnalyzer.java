package dan.vjtest.sandbox.annotations.temp.greet;

import org.objectweb.asm.*;

import java.io.PrintStream;


/**
 * @author Alexander Dovzhikov
 */
public class GreetMethodAnalyzer extends MethodAdapter {
    private final String methodName;

    private boolean greet = false;
    private boolean bye = false;

    public GreetMethodAnalyzer(MethodVisitor mv, String name) {
        super(mv);

        methodName = name;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        greet |= Type.getDescriptor(Greet.class).equals(desc);
        bye |= Type.getDescriptor(Bye.class).equals(desc);

        return super.visitAnnotation(desc, visible);
    }

    @Override
    public void visitCode() {
        super.visitCode();

        if (greet) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, Type.getInternalName(System.class), "out", Type.getDescriptor(PrintStream.class));
            mv.visitLdcInsn("Hello, " + methodName);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(PrintStream.class), "println",
                    Type.getMethodDescriptor(Type.VOID_TYPE, new Type[] {Type.getType(String.class)}));
        }
    }

    @Override
    public void visitInsn(int opcode) {
        if (bye && opcode >= Opcodes.IRETURN && opcode <= Opcodes.RETURN || opcode == Opcodes.ATHROW) {
            mv.visitFieldInsn(Opcodes.GETSTATIC, Type.getInternalName(System.class), "out", Type.getDescriptor(PrintStream.class));
            mv.visitLdcInsn("Goodbye, " + methodName);
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, Type.getInternalName(PrintStream.class), "println",
                    Type.getMethodDescriptor(Type.VOID_TYPE, new Type[] {Type.getType(String.class)}));
        }

        mv.visitInsn(opcode);
    }
}
