package mate.academy.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T create(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

    T update(T t);

    boolean deleteById(Long id);
}
