package dan.vjtest.sandbox.annotations.temp.greet;

import org.objectweb.asm.*;

import java.io.IOException;

/**
 * @author Alexander Dovzhikov
 */
public class GreetAnalyzer extends ClassAdapter {

    public static void main(String[] args) throws IOException {
        ClassReader reader = new ClassReader(Guinea.class.getName());
        ClassWriter writer = new ClassWriter(reader, 0);

        reader.accept(new GreetAnalyzer(writer), 0);
    }

    public GreetAnalyzer(ClassVisitor cv) {
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
