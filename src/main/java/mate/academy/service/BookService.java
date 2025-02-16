package mate.academy.service;

import mate.academy.model.Book;

import java.util.List;

public interface BookService {
    Book create(Book book);

    Book get(Long id);

    List<Book> findAll();

    Book update(Book book);

    boolean deleteById(Long id);
}
