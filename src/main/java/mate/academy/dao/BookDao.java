package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {

    Book create(Book book);

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);

    //THIS METHOD FOR FAST TEST AND CLEAN, don't use in a real application
    boolean dropDataBase();
}
