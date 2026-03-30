package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Dao
public interface BookDao {
    List<Book> findAll();
    Optional<Book> findById(Long id);
    Book create(Book book) throws SQLException;
    boolean deleteById(Long id);
}