package mate.academy.service;

import java.util.List;
import mate.academy.model.Book;

public interface BookService {

    Book create(Book book);

    Book getById(Long id);

    List<Book> getAll();

    Book update(Book book);

    boolean deleteById(Long id);

}
