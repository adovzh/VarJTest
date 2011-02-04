package dan.vjtest.sandbox.annotations;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author Alexander Dovzhikov
 */
public class AnnotationsAgent implements ClassFileTransformer {

    public static void premain(String argument, Instrumentation instrumentation) {
        instrumentation.addTransformer(new AnnotationsAgent());
    }

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.endsWith("Guinea"))
            return classfileBuffer;

        ClassReader reader = new ClassReader(classfileBuffer);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

        reader.accept(new AnnotationsClassVisitor(writer), 0);

        return writer.toByteArray();
    }
}
