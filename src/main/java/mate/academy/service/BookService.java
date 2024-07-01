package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookService {
    Book create(Book book);

    Optional<Book> readID(Long id);

    List<Book> readAll();

    Book update(Book book);

    boolean delete(Long id);
}
