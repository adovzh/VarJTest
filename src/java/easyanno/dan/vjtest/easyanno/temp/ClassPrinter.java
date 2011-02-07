package dan.vjtest.easyanno.temp;

import org.objectweb.asm.*;

import java.io.IOException;

/**
 * @author Alexander Dovzhikov
 */
public class ClassPrinter implements ClassVisitor {

    private final int debug = 1;

    public static void main(String[] args) throws IOException {
        ClassPrinter cp = new ClassPrinter();
        ClassReader cr = new ClassReader("dan.vjtest.sandbox.easyanno.temp.ClassPrinter");
        cr.accept(cp, 0);
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        System.out.println(name + " extends " + superName + " {");
    }

    public void visitSource(String source, String debug) {
    }

    public void visitOuterClass(String owner, String name, String desc) {
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public void visitAttribute(Attribute attr) {
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        System.out.println("   " + desc + " " + name);
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        System.out.println("   " + name + ":" + desc);
        return null;
    }

    public void visitEnd() {
        System.out.println("}");
    }
}
