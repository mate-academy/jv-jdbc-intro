package mate.academy.dao.impl;

import mate.academy.dao.BookDao;
import mate.academy.model.Book;

import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public Book update(Book book) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
