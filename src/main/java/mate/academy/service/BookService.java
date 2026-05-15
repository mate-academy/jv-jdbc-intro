package mate.academy.service;

import mate.academy.model.Book;

public interface BookService {
    Book save(Book book);

    Book get(Long id);

    Book update(Book book);

    boolean delete(Long id);
}
