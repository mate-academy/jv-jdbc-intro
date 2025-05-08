package mate.academy.service;

import mate.academy.model.Book;

public interface BookService {
    Book create(Book book);

    Book get(Long id);

    Book update(Book book);

    boolean deleteById(Long id);
}
