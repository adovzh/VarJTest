package dan.vjtest.sandbox.annotations;

import org.objectweb.asm.*;

import java.io.IOException;

/**
 * @author Alexander Dovzhikov
 */
public class AnnotationsClassVisitor extends ClassAdapter {

    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader(Guinea.class.getName());
        ClassWriter writer = new ClassWriter(reader, 0);

        reader.accept(new AnnotationsClassVisitor(writer), 0);
    }

    public AnnotationsClassVisitor(ClassVisitor cv) {
        super(cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);

        if (methodVisitor != null) {
            methodVisitor = new GreetMethodVisitor(new ByeMethodVisitor(new NotNullMethodVisitor(methodVisitor), name, desc), name, desc);
        }

        return methodVisitor;
    }
}
