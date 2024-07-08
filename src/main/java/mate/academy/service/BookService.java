package mate.academy.service;

import mate.academy.models.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    Book get(Long id);

    List<Book> getAll();

    Book update(Book book);

    boolean delete(Book book);
}
