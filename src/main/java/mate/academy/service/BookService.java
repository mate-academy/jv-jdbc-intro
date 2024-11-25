package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.entity.Book;

public interface BookService {
    Book createBook(Book book);

    Optional<Book> getBookById(Long id);

    List<Book> findAllBooks();

    Book updateBook(Long id,Book book);

    boolean deleteBookById(Long id);
}
