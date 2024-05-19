package mate.academy.service;

import mate.academy.model.Book;

import java.util.List;

public interface BookService {

    Book create(Book book);

    Book getById(Long id);

    List<Book> getAll();

    Book update(Book book);

    boolean deleteById(Long id);

}
