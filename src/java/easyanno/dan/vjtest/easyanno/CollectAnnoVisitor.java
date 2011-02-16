package dan.vjtest.easyanno;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.EmptyVisitor;
import org.objectweb.asm.commons.Method;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Alexander Dovzhikov
 */
public class CollectAnnoVisitor extends EmptyVisitor {

    private final Set<String> annotations;
    private final Map<Method, Set<String>> results;

    public CollectAnnoVisitor(Set<Class<? extends Annotation>> annotations) {
        this.annotations = wrap(annotations);
        this.results = new HashMap<Method, Set<String>>();
    }

    private static Set<String> wrap(Set<Class<? extends Annotation>> annotations) {
        Set<String> descriptors = new HashSet<String>(annotations.size());

        for (Class<? extends Annotation> clazz : annotations) {
            descriptors.add(Type.getDescriptor(clazz));
        }

        return descriptors;
    }

    public Map<Method, Set<String>> getResults() {
        return results;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Method method = new Method(name, desc);
        results.put(method, new HashSet<String>());

        return new MethodExplorer(method);
    }

    private class MethodExplorer extends EmptyVisitor {
        private final Method method;

        public MethodExplorer(Method method) {
            this.method = method;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
            Class<?> clazz = Type.getType(desc).getClass();

            if (annotations.contains(desc)) {
                results.get(method).add(desc);
            }

            return null;
        }
    }
}
