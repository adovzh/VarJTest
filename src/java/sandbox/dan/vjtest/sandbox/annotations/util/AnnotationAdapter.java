package dan.vjtest.sandbox.annotations.util;

import org.objectweb.asm.AnnotationVisitor;

/**
 * @author Alexander Dovzhikov
 */
public class AnnotationAdapter implements AnnotationVisitor {
    private final AnnotationVisitor adaptee;

    public AnnotationAdapter(AnnotationVisitor adaptee) {
        this.adaptee = adaptee;
    }

    public void visit(String name, Object value) {
        adaptee.visit(name, value);
    }

    public void visitEnum(String name, String desc, String value) {
        adaptee.visitEnum(name, desc, value);
    }

    public AnnotationVisitor visitAnnotation(String name, String desc) {
        return adaptee.visitAnnotation(name, desc);
    }

    public AnnotationVisitor visitArray(String name) {
        return adaptee.visitArray(name);
    }

    public void visitEnd() {
        adaptee.visitEnd();
    }
}
