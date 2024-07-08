package mate.academy.service;

import java.util.List;
import mate.academy.models.Book;

public interface BookService {
    Book save(Book book);

    Book get(Long id);

    List<Book> getAll();

    Book update(Book book);

    boolean delete(Book book);
}
