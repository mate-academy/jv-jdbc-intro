package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

@mate.academy.lib.Dao
public interface Dao<T> {
    Book create(T t);

    Optional<T> findById(Long id);

    List<T> findAll();

    T update(T t);

    boolean deleteById(Long id);
}
