
package org.neogroup.warp.utils;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

/**
 * Class scanner to find clases with certain filters
 */
public class Scanner {

    private static final String CLASS_EXTENSION = ".class";

    private Set<URI> classPaths;

    /**
     * Constructor of the scanner
     * Adds the classloader classpath by default
     */
    public Scanner() {
        classPaths = new HashSet<>();
        addClassPaths(Thread.currentThread().getContextClassLoader());
    }

    /**
     * Adds a new classLoader for finding classes
     * @param classLoader Classloader to be loaded
     */
    public void addClassPaths (ClassLoader classLoader) {
        try {
            Enumeration<URL> roots = classLoader.getResources("");
            while (roots.hasMoreElements()) {
                URL root = roots.nextElement();
                addClassPath(root.toURI());
            }
        }
        catch (Exception ex) {}
    }

    /**
     * Add a classpath by an uri
     * @param classPath uri classpath to be added
     */
    public void addClassPath (URI classPath) {
        classPaths.add(classPath);
    }

    /**
     * Find classes in the given claspaths
     * @return Classes retrieved
     */
    public Set<Class> findClasses () {
        return findClasses((Class clazz) -> { return true; });
    }

    /**
     * Find clases with the given class filter
     * @param classFilter Filter for the classes
     * @return Classes retrieved
     */
    public Set<Class> findClasses (ClassFilter classFilter) {

        Set<Class> classes = new HashSet<>();
        for (URI classPath : classPaths) {
            try {
                File resource = new File(classPath);
                if(resource.isDirectory()) {
                    findClasses(classes, resource, "", classFilter);
                }
                else {
                    JarFile jar = new JarFile(resource);
                    Enumeration<JarEntry> en = jar.entries();
                    while (en.hasMoreElements()) {
                        JarEntry entry = en.nextElement();
                        findJarClasses(classes, jar, entry, "", classFilter);
                    }
                }
            }
            catch (Exception ex) {}
        }
        return classes;
    }

    /**
     * Find jar classes in a jar
     * @param classes Classes being collected
     * @param jf jar file
     * @param entry jar entry
     * @param baseName base class name
     * @param classFilter class filter
     * @throws Exception
     */
    private void findJarClasses (Set<Class> classes, JarFile jf, JarEntry entry, String baseName, ClassFilter classFilter) throws Exception {

        String entryName = entry.getName();
        if (entry.isDirectory()) {
            try (JarInputStream jis = new JarInputStream(jf.getInputStream(entry))){
                findJarClasses(classes, jf, jis.getNextJarEntry(), baseName + "." + entryName, classFilter);
            }
        }
        else {
            if (entryName.endsWith(CLASS_EXTENSION)) {
                String className = baseName.substring(0, baseName.length() - CLASS_EXTENSION.length());
                Class clazz = getClass(className);
                if (clazz != null) {
                    if (classFilter.accept(clazz)) {
                        classes.add(clazz);
                    }
                }
            }
        }
    }

    /**
     * Find clases
     * @param classes
     * @param file
     * @param baseName
     * @param classFilter
     */
    private void findClasses (Set<Class> classes, File file, String baseName, ClassFilter classFilter) {

        if(file.isDirectory()){
            for(File subFile : file.listFiles()) {
                if(baseName.isEmpty()){
                    findClasses(classes, subFile, subFile.getName(), classFilter);
                }
                else {
                    findClasses(classes, subFile, baseName + "." + subFile.getName(), classFilter);
                }
            }
        }
        else {
            if(file.getName().endsWith(CLASS_EXTENSION)) {
                String className = baseName.substring(0, baseName.length() - CLASS_EXTENSION.length());
                Class clazz = getClass(className);
                if (clazz != null) {
                    if (classFilter.accept(clazz)) {
                        classes.add(clazz);
                    }
                }
            }
        }
    }

    private Class getClass (String className) {
        Class clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (Exception ex) {}
        return clazz;
    }

    public interface ClassFilter {

        public boolean accept (Class clazz);
    }
}