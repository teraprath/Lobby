package net.spigotcloud.lobby.module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleLoader {

    /**
     * Loads the specified file with instance of the super class
     *
     * @param file       loaded file
     * @param superClass super class, used to initialize the loaded jar's providers.
     *                   <b>Required because we can't require instance of the super class</b>
     * @param <T> super class, used to initialize the loaded jar's providers
     * @return super class if load was accomplished
     * @throws NullPointerException        if file does not exist
     * @throws NotJarException             if file isn't jar
     * @throws FileCannotBeLoadedException if file cannot be loaded (does not contain any files which extend the super class)
     */
    public <T> T load(File file, Class<T> superClass) {
        try {
            Class<? extends T> raw = getRawClass(file, superClass);
            T instance = raw.getDeclaredConstructor().newInstance();
            if (instance != null) {
                return instance;
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        throw new FileCannotBeLoadedException("File '" + file.getAbsolutePath() + "' cannot be loaded. Reason: unknown");
    }

    /**
     * Gets the raw class that extends the super class in the loaded file
     *
     * @param file       loaded file
     * @param superClass super class, used to initialize the loaded jar's providers.
     *                   <b>Required because we can't require instance of the super class</b>
     * @param <T> super class, used to initialize the loaded jar's providers
     * @return class that extends super class if load was accomplished
     * @throws NullPointerException        if file does not exist
     * @throws NotJarException             if file isn't jar
     * @throws FileCannotBeLoadedException if file cannot be loaded (does not contain any files which extend the super class)
     */
    public <T> Class<? extends T> getRawClass(File file, Class<T> superClass) {
        if (!file.exists()) {
            throw new NullPointerException("File '" + file.getAbsolutePath() + "' does not exist.");
        }
        if (!file.getName().endsWith(".jar")) {
            throw new NotJarException("File '" + file.getAbsolutePath() + "' is not jar.");
        }
        try {
            Set<String> classes = new HashSet<>();
            JarFile jar = new JarFile(file);
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
                    continue;
                }
                classes.add(entry.getName().substring(0, entry.getName().length() - 6).replace("/", "."));
            }
            ClassLoader classLoader = URLClassLoader.newInstance(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());
            for (String className : classes) {
                Class<?> loaded = Class.forName(className, true, classLoader);
                if (loaded.isAssignableFrom(superClass)) {
                    return loaded.asSubclass(superClass);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new FileCannotBeLoadedException("File '" + file.getAbsolutePath() + "' cannot be loaded. No classes were found extending subclass '" + superClass.getSimpleName() + "'");
    }

    /**
     * Simple runtime exception, showing that a file is not a jar
     */
    public static class NotJarException extends RuntimeException {

        public NotJarException(String message) {
            super(message);
        }
    }

    /**
     * Simple runtime exception, showing that a file cannot be loaded
     */
    public static class FileCannotBeLoadedException extends RuntimeException {

        public FileCannotBeLoadedException(String message) {
            super(message);
        }
    }
}
