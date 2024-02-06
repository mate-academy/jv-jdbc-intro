package mate.academy.dao;

import java.util.List;
import java.util.Optional;
import mate.academy.model.Book;

public class BookDaoImpl implements Dao<Book> {
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
