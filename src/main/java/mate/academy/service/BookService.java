package mate.academy.service;

import java.util.List;
import java.util.Optional;
import mate.academy.entity.Book;

public interface BookService {
    Book createBook(Book book);

    Optional<Book> getBookById(int id);

    List<Book> findAllBooks();

    Book updateBook(int id,Book book);

    boolean deleteBookById(int id);
}
