package org.neogroup.warp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class WarpLauncher {

    private static final String START_CLASS_ATTRIBUTE_NAME = "Start-Class";
    private static final String WAR_FILE_PARAMETER_NAME = "org.neogroup.warp.warFilename";
    private static final String WEB_ROOT_PARAMETER_NAME = "org.neogroup.warp.webRoot";

    public static void main(String[] args) throws Exception {

        try {

            URL url = WarpLauncher.class.getProtectionDomain().getCodeSource().getLocation();
            String filename = url.getFile();
            String warFilename = filename.substring(filename.lastIndexOf(File.separator) + 1);
            String warName = warFilename.substring(0, warFilename.indexOf("."));
            Path warFolderPath = Files.createTempDirectory(warName);
            File warFolderFile = warFolderPath.toFile();
            warFolderFile.deleteOnExit();

            System.out.println ("Extracting \"" + warFilename + "\" to \"" + warFolderPath + "\" ...");
            System.out.println ("================================================");
            System.out.println ();

            JarFile warFile = new JarFile(url.getFile());
            Enumeration enumEntries = warFile.entries();
            while (enumEntries.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumEntries.nextElement();
                System.out.println ("Extracting \"" + jarEntry.getName() + "\" ...");

                File file = new File(warFolderPath.toString(), jarEntry.getName());
                if(!file.exists()) {
                    file.getParentFile().mkdirs();
                    file = new File(warFolderPath.toString(), jarEntry.getName());
                }
                if(jarEntry.isDirectory()) {
                    continue;
                }
                try (InputStream inputStream = warFile.getInputStream(jarEntry); FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[2048];
                    int readSize = inputStream.read(buffer);
                    while (readSize >= 0) {
                        fileOutputStream.write(buffer, 0, readSize);
                        fileOutputStream.flush();
                        readSize = inputStream.read(buffer);
                    }
                }
            }
            warFile.close();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println ();
                System.out.println ("Deleting war path \"" + warFolderPath + "\" ...");
                System.out.println ("================================================");
                try {
                    Files.walk(warFolderPath, FileVisitOption.FOLLOW_LINKS)
                            .sorted(Comparator.reverseOrder())
                            .map(Path::toFile)
                            .forEach(File::delete);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }));

            System.out.println ();
            System.out.println ("Executing war path \"" + warFolderPath + "\" ...");
            System.out.println ("================================================");
            System.out.println ();

            Path classesPath = warFolderPath.resolve("WEB-INF").resolve("classes");
            Path libsPath = warFolderPath.resolve("WEB-INF").resolve("lib");

            List<URL> classPathUrls = new ArrayList<>();
            classPathUrls.add(classesPath.toUri().toURL());
            File[] libs = libsPath.toFile().listFiles();
            for (File lib : libs) {
                classPathUrls.add(lib.toURI().toURL());
            }

            URLClassLoader urlClassLoader = new URLClassLoader (classPathUrls.toArray(new URL[0]), WarpLauncher.class.getClassLoader());

            String startClassName = null;
            URL manifestResource = WarpLauncher.class.getClassLoader().getResource("META-INF/MANIFEST.MF");
            try (InputStream inputStream = manifestResource.openStream()) {
                Manifest manifest = new Manifest(inputStream);
                Attributes attributes = manifest.getMainAttributes();
                startClassName = attributes.getValue (START_CLASS_ATTRIBUTE_NAME);
            }

            if (startClassName == null) {
                throw new Exception ("Manifest attribute \"" + START_CLASS_ATTRIBUTE_NAME + "\" not found !!");
            }

            System.setProperty(WAR_FILE_PARAMETER_NAME, warFilename);
            System.setProperty(WEB_ROOT_PARAMETER_NAME, warFolderPath.toString());
            Class mainClass = Class.forName(startClassName, true, urlClassLoader);
            Method method = mainClass.getDeclaredMethod("main", String[].class);
            method.invoke(null, new Object[]{new String[]{"--warFile=" + warFilename, "--webRoot=" + warFolderPath}});
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
