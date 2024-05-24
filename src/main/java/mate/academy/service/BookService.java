package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public interface BookService {
    Book createBook(Book book);

    Optional<Book> findBookById(Long id);

    List<Book> findAllBooks();

    Book updateBook(Book book);

    boolean deleteBookById(Long id);

    boolean deleteAll();
}
