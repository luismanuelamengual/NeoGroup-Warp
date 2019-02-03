
package org.neogroup.warp.utils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * Utilitary functions for reflection
 */
public class ReflectionUtils {

    /**
     * Finds an annotation in the class hierarchy
     * @param clazz Class to obtain the annotation
     * @param annotationType Annotation to find
     * @param <A> Annotation Type to obtain
     * @return
     */
    public static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType) {
        return findAnnotation(clazz, annotationType, new HashSet<Annotation>());
    }

    /**
     * Finds an annotation in the class hierarchy
     * @param clazz Class to obtain the annotation
     * @param annotationType Annotation to find
     * @param visited Indicates where the class has been visited
     * @param <A> Annotation Type to obtain
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <A extends Annotation> A findAnnotation(Class<?> clazz, Class<A> annotationType, Set<Annotation> visited) {

        try {
            Annotation[] anns = clazz.getDeclaredAnnotations();
            for (Annotation ann : anns) {
                if (ann.annotationType() == annotationType) {
                    return (A) ann;
                }
            }
            for (Annotation ann : anns) {
                if (visited.add(ann)) {
                    A annotation = findAnnotation(ann.annotationType(), annotationType, visited);
                    if (annotation != null) {
                        return annotation;
                    }
                }
            }
        }
        catch (Exception ex) {
            return null;
        }

        for (Class<?> ifc : clazz.getInterfaces()) {
            A annotation = findAnnotation(ifc, annotationType, visited);
            if (annotation != null) {
                return annotation;
            }
        }

        Class<?> superclass = clazz.getSuperclass();
        if (superclass == null || Object.class == superclass) {
            return null;
        }
        return findAnnotation(superclass, annotationType, visited);
    }
}
