package mate.academy.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.exceptions.DataProcessingException;
import mate.academy.model.Book;

public interface BookDao {
    Book create(Book book) throws DataProcessingException;

    Optional<Book> findById(Long id);

    List<Book> findAll() throws DataProcessingException;

    Book update(Book book) throws DataProcessingException;

    boolean deleteById(Long id) throws DataProcessingException;
}
