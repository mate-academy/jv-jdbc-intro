package mate.academy.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book);

    Optional<Book> findById(BigInteger id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(BigInteger id);
}
