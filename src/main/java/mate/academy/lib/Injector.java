package mate.academy.lib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mate.academy.exception.DataProcessingException;

public class Injector {
    private static final Map<String, Injector> injectors = new HashMap<>();
    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final List<Class<?>> classes = new ArrayList<>();

    private Injector(String mainPackageName) {
        try {
            classes.addAll(getClasses(mainPackageName));
        } catch (IOException | ClassNotFoundException e) {
            throw new DataProcessingException("Can't get information about classes", e);
        }
    }

    public static Injector getInstance(String mainPackageName) {
        if (injectors.containsKey(mainPackageName)) {
            return injectors.get(mainPackageName);
        }
        Injector injector = new Injector(mainPackageName);
        injectors.put(mainPackageName, injector);
        return injector;
    }

    public Object getInstance(Class<?> interfaceClazz) {
        Object clazzImplementationInstance = null;
        Class<?> clazz = findImplementation(interfaceClazz);
        if (!clazz.isAnnotationPresent(Dao.class)) {
            throw new DataProcessingException("This class is not a Dao implementation", null);
        }
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(Dao.class)) {
                Object fieldInstance = getInstance(field.getType());
                clazzImplementationInstance = createNewInstance(clazz);
                try {
                    field.setAccessible(true);
                    field.set(clazzImplementationInstance, fieldInstance);
                } catch (IllegalAccessException e) {
                    throw new DataProcessingException("Can't initialize field "
                            + field.getName(), e);
                }
            }
        }
        if (clazzImplementationInstance == null) {
            clazzImplementationInstance = createNewInstance(clazz);
        }
        return clazzImplementationInstance;
    }

    private Object createNewInstance(Class<?> clazz) {
        if (instances.containsKey(clazz)) {
            return instances.get(clazz);
        }
        try {
            Constructor<?> constructor = clazz.getConstructor();
            Object instance = constructor.newInstance();
            instances.put(clazz, instance);
            return instance;
        } catch (ReflectiveOperationException e) {
            throw new DataProcessingException("Can't create a new instance of "
                    + clazz.getName(), e);
        }
    }

    private Class<?> findImplementation(Class<?> interfaceClazz) {
        if (interfaceClazz.isInterface() || interfaceClazz.isAnnotationPresent(Dao.class)) {
            for (Class<?> clazz : classes) {
                if (interfaceClazz.isAssignableFrom(clazz) && !clazz.isInterface()) {
                    return clazz;
                }
            }
        }
        return interfaceClazz;
    }

    private static List<Class<?>> getClasses(String packageName)
            throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile().replaceAll("%20", " ")));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    private static List<Class<?>> findClasses(File directory, String packageName)
            throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    assert !file.getName().contains(".");
                    classes.addAll(findClasses(file, packageName + "."
                            + file.getName()));
                } else if (file.getName().endsWith(".class")) {
                    classes.add(Class.forName(packageName + '.'
                            + file.getName().substring(0, file.getName().length() - 6)));
                }
            }
        }
        return classes;
    }
}
