package mate.academy.dao;

import java.util.List;
import java.util.Optional;

public interface BookDao<K, E> {
    E create(E book);

    Optional<E> findById(K id);

    List<E> findAll();

    E update(E book);

    boolean delete(K id);
}
