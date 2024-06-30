package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao{
    @Override
    public void createTable() {

    }

    @Override
    public Book save(Book book) {
        return null;
    }

    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return List.of();
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
