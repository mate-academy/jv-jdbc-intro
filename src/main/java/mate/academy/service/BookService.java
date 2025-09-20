package mate.academy.service;

import java.util.List;
import mate.academy.models.Book;

public interface BookService {
    Book create(Book book);

    Book findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean delete(Long id);
}
