package mate.academy.lib;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Injector {
    private static final Map<String, Injector> injectors = new HashMap<>();
    private final List<Class<?>> classes = new ArrayList<>();

    private Injector(String mainPackageName) {
        try {
            classes.addAll(getClasses(mainPackageName));
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Can't get information about all classes", e);
        }
    }

    public static Injector getInstance(String mainPackageName) { // перевірка чи існує інжектор (в мапі) для даного пакета,
        if (injectors.containsKey(mainPackageName)) {
            return injectors.get(mainPackageName);
        }
        Injector injector = new Injector(mainPackageName); // якщо ні - під капотом створ новий інжектор
        injectors.put(mainPackageName, injector);          // і дод в мапу
        return injector;                                   // щоб для одного пакета створювався один інжектор
    }

    public Object getInstance(Class<?> certainInterface) {
        Class<?> clazz = findClassExtendingInterface(certainInterface); // коли знайшли імплементацію (метод нижче)
        return createInstance(clazz);  // робимо обєкт знайденого класу-імплементації (метод ще нижче)
    }

    private Class<?> findClassExtendingInterface(Class<?> certainInterface) {  // BookDao.class
        for (Class<?> clazz : classes) {     // проходимось по масиву усіх наяв класів в пакеті
            Class<?>[] interfaces = clazz.getInterfaces();  // в кожного з них (Book, BookDao, BookDaoImpl...) берем інтерфейси що наслідуються цим класом і робим масив з цих інтерфейсів
            for (Class<?> singleInterface : interfaces) {  // по списку інтерф (класа BookDaoImpl) пробіг
                if (singleInterface.equals(certainInterface)    // і порівн з BookDao.class
                        && clazz.isAnnotationPresent(Dao.class)) {  // і чи цей клас (BookDaoImpl) з якого ми власне витягували інтерфейси має аннотацію @Dao
                    return clazz;  // повернення знайденого класу-імплементації BookDaoImpl
                }
            }
        }
        throw new RuntimeException("Can't find class which implements "
                + certainInterface.getName()
                + " interface and has valid annotation (Dao or Service)");
    }

    private Object createInstance(Class<?> clazz) {  // робимо обєкт класу-імплементації (за допомогою класів Reflection API)
        Object newInstance;
        try {
            Constructor<?> classConstructor = clazz.getConstructor();
            newInstance = classConstructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Can't create object of the class", e);
        }
        return newInstance;
    }

    /**
     * Scans all classes accessible from the context class loader which
     * belong to the given package and subpackages.
     *
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException if the class cannot be located
     * @throws IOException            if I/O errors occur
     */
    private static List<Class<?>> getClasses(String packageName)  // використовується в приватному конструкторі вище
            throws IOException, ClassNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            throw new RuntimeException("Class loader is null");
        }
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and subdirs.
     *
     * @param directory   The base directory
     * @param packageName The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException if the class cannot be located
     */
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
                    if (file.getName().contains(".")) {
                        throw new RuntimeException("File name shouldn't consist point.");
                    }
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
