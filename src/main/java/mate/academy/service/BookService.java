package mate.academy.service;

import java.util.List;
import mate.academy.model.Book;

public interface BookService {
    Book createBook(Book book);

    Book getBookById(Long id);

    List<Book> getAllBooks();

    Book updateBook(Book book);

    boolean deleteBook(Long id);
}
