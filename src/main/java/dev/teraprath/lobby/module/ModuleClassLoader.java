package dev.teraprath.lobby.module;

import dev.teraprath.lobby.Lobby;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;

public class ModuleClassLoader extends URLClassLoader {

    private static List<String> blacklistPackageName = Arrays.asList("org.bukkit.", "net.minecraft.");

    static {
        try {
            Method method = ClassLoader.class.getDeclaredMethod("registerAsParallelCapable");
            if (method != null) {
                boolean oldAccessible = method.isAccessible();
                method.setAccessible(true);
                method.invoke(null);
                method.setAccessible(oldAccessible);
                Bukkit.getLogger().log(Level.INFO, "Set QuestClassLoader as parallel capable");
            }
        } catch (NoSuchMethodException ignored) {
        } catch (Exception var3) {
            Bukkit.getLogger().log(Level.WARNING, "Error setting QuestClassLoader as parallel capable", var3);
        }
    }

    private final ConcurrentHashMap<String, Class<?>> classes = new ConcurrentHashMap<>();
    private Class<? extends IModule> moduleMainClass;
    private File file;
    private ModuleDescriptionFile description;
    private ModuleLoader loader;
    private boolean enabled;

    @SuppressWarnings("unchecked")
    ModuleClassLoader(ModuleLoader loader, ClassLoader parent, ModuleDescriptionFile description, File file) throws MalformedURLException, MalformedURLException {
        super(new URL[]{file.toURI().toURL()}, parent);
        // Validate.notNull(loader, "Loader can not be null");
        this.file = file;
        this.description = description;
        this.loader = loader;

        Class mainClass;

        try {
            mainClass = Class.forName(description.getMain(), true, this);
        } catch (ClassNotFoundException e) {
            Lobby.getInstance().getLogger().severe("Cannot find main class `" + description.getMain() + "`");
            return;
        }

        if (IModule.class.isAssignableFrom(mainClass)) {
            this.moduleMainClass = (Class<? extends IModule>) mainClass;
        } else {
            Lobby.getInstance().getLogger().severe("Declared main class `" + description.getMain() + "` does not implement from class IModule");
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        return findClass(name, true);
    }

    private boolean isClassNameBlacklist(String name) {
        for (String str : blacklistPackageName) {
            if (name.startsWith(str))
                return true;
        }
        return false;
    }

    Class<?> findClass(String name, boolean checkGlobal) throws ClassNotFoundException {
        if (!isClassNameBlacklist(name)) {
            Class<?> result = this.classes.get(name);
            if (result == null) {
                if (checkGlobal) {
                    result = this.loader.getClassByName(name);
                }

                if (result == null) {
                    result = super.findClass(name);
                    if (result != null) {
                        this.loader.setClass(name, result);
                    }
                }
                this.classes.put(name, result);
            }

            return result;
        } else {
            throw new ClassNotFoundException(name);
        }
    }

    public void clearClasses() {
        classes.clear();
    }

    public Set<String> getClasses() {
        return classes.keySet();
    }

    public IModule getModule() {
        try {
            return moduleMainClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
