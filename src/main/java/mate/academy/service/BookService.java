package mate.academy.service;

import java.util.List;
import mate.academy.lib.Service;
import mate.academy.model.Book;

@Service
public interface BookService {
    Book create(Book book);

    Book findById(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}
