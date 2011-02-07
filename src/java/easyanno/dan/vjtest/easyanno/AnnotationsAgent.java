package dan.vjtest.easyanno;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.commons.Method;

import java.lang.annotation.Annotation;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.*;

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

        // TODO: analyse code for the annotations of interest first
        // TODO: change the byte code only after that
        // TODO: build the chain of visitors per annotation

        ClassReader reader = new ClassReader(classfileBuffer);
        ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

        CollectAnnoVisitor analyzer = new CollectAnnoVisitor(new HashSet<Class<? extends Annotation>>(Arrays.asList(Greet.class, Bye.class, NotNull.class)));
        reader.accept(analyzer, 0);

        Map<Method, Set<String>> results = analyzer.getResults();

        for (Map.Entry<Method, Set<String>> entry : results.entrySet()) {
            Method method = entry.getKey();
            Set<String> classes = entry.getValue();

            System.out.println("Method: " + method);

            for (String anno : classes) {
                System.out.println("Anno: " + anno);
            }
        }

        reader.accept(new AnnotationsClassVisitor(writer), 0);

        return writer.toByteArray();
    }
}
