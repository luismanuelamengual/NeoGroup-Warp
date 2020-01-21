
package org.neogroup.warp.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Class scanner to find clases with certain filters
 */
public class Scanner {

    private static final String CLASS_EXTENSION = ".class";

    /**
     * Find classes in the given claspaths
     * @return Classes retrieved
     */
    public static Set<Class> findClasses () {
        return findClasses((Class clazz) -> { return true; });
    }

    /**
     * Find clases with the given class filter
     * @param classFilter Filter for the classes
     * @return Classes retrieved
     */
    public static Set<Class> findClasses (ClassFilter classFilter) {
        Set<Class> classes = new HashSet<>();
        List<File> classLocations = getClassLocationsForCurrentClasspath();
        for (File file : classLocations) {
            findClassesFromPath(file, classes, classFilter);
        }
        return classes;
    }

    /**
     * Find classes in a path
     * @param path
     * @param classes
     * @param classFilter
     */
    private static void findClassesFromPath(File path, Set<Class> classes, ClassFilter classFilter) {
        if (path.isDirectory()) {
            findClassesFromDirectory(path, classes, classFilter);
        } else {
            findClassesFromJarFile(path, classes, classFilter);
        }
    }

    /**
     * Find classes in a Jar file
     * @param path
     * @param classes
     * @param classFilter
     */
    private static void findClassesFromJarFile(File path, Set<Class> classes, ClassFilter classFilter) {
        try {
            if (path.canRead()) {
                JarFile jar = new JarFile(path);
                Enumeration<JarEntry> en = jar.entries();
                while (en.hasMoreElements()) {
                    JarEntry entry = en.nextElement();
                    if (entry.getName().endsWith("class")) {
                        String className = fromFileToClassName(entry.getName());
                        try {
                            Class clazz = Class.forName(className);
                            if (classFilter.accept(clazz)) {
                                classes.add(clazz);
                            }
                        } catch (Throwable throwable) {}
                    }
                }
            }
        }
        catch (Exception e) {}
    }

    /**
     * Find classes in a directory
     * @param path
     * @param classes
     * @param classFilter
     */
    private static void findClassesFromDirectory(File path, Set<Class> classes, ClassFilter classFilter) {
        List<File> jarFiles = listFiles(path, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        }, false);
        for (File file : jarFiles) {
            findClassesFromJarFile(path, classes, classFilter);
        }

        List<File> classFiles = listFiles(path, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }
        }, true);

        int substringBeginIndex = path.getAbsolutePath().length() + 1;
        for (File classfile : classFiles) {
            String className = classfile.getAbsolutePath().substring(substringBeginIndex);
            className = fromFileToClassName(className);
            try {
                Class clazz = Class.forName(className);
                if (classFilter.accept(clazz)) {
                    classes.add(clazz);
                }
            } catch (Throwable e) {
            }
        }
    }

    /**
     * Get current classpath locations
     * @return
     */
    private static List<File> getClassLocationsForCurrentClasspath() {
        List<File> urls = new ArrayList<File>();
        String javaClassPath = System.getProperty("java.class.path");
        if (javaClassPath != null) {
            for (String path : javaClassPath.split(File.pathSeparator)) {
                urls.add(new File(path));
            }
        }
        return urls;
    }

    /**
     * List files in directory
     * @param directory
     * @param filter
     * @param recurse
     * @return
     */
    private static List<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
        List<File> files = new ArrayList<>();
        File[] entries = directory.listFiles();
        for (File entry : entries) {
            if (filter == null || filter.accept(directory, entry.getName())) {
                files.add(entry);
            }
            if (recurse && entry.isDirectory()) {
                files.addAll(listFiles(entry, filter, recurse));
            }
        }
        return files;
    }

    /**
     * Get classname from filename
     * @param fileName
     * @return
     */
    private static String fromFileToClassName(final String fileName) {
        return fileName.substring(0, fileName.length() - 6).replaceAll("/|\\\\", "\\.");
    }

    public interface ClassFilter {
        public boolean accept (Class clazz);
    }
}