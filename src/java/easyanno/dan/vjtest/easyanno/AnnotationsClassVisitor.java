package dan.vjtest.easyanno;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author Alexander Dovzhikov
 */
public class AnnotationsClassVisitor extends ClassAdapter {

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
