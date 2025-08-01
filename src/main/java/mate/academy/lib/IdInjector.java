package mate.academy.lib;

import java.lang.reflect.Field;

public class IdInjector {
    //TODO make method more abstract using generics and add check fot id field data type
    public static void injectIdFromDB(Object entity, Long idValue) {
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(ID.class)) {
                field.setAccessible(true);
                try {
                    field.setLong(entity, idValue);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Failed to inject ID field into entity: "
                            + entity.getClass().getSimpleName(), e);
                }
            }
        }
    }
}
