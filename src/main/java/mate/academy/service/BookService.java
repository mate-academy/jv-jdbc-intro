package mate.academy.service;

import mate.academy.model.Book;

public interface BookService {
    Book save(Long id);

    Book update(Book book);

    boolean delete(Book book);
}
