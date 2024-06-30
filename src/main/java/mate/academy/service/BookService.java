package mate.academy.service;

import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book create(Book book);
    Optional<Book> readID(Long id);
    List<Book> readAll();
    Book update(Book book);
    boolean delete(Long id);
}
