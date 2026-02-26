package mate.academy.dao;

import mate.academy.lib.Dao;
import mate.academy.model.Book;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    /**
     * create: INSERT ... RETURN_GENERATED_KEYS, встанови id згенерований.
     * @param book
     * @return
     */
    @Override
    public Book create(Book book) {
        return null;
    }

    /**
     * findById: SELECT ... WHERE id = ?, якщо запис знайдений — поверни Optional.of(book),
     * інакше Optional.empty().
     * @param id
     * @return
     */
    @Override
    public Optional<Book> findById(Long id) {
        return Optional.empty();
    }

    /**
     * findAll: SELECT * FROM books, пробіжи ResultSet, збирай у список.
     * @return
     */
    @Override
    public List<Book> findAll() {
        return List.of();
    }

    /**
     * update: UPDATE books SET title = ?, price = ? WHERE id = ?, поверни оновлений об’єкт
     * або кинь DataProcessingException при помилці.
     * @param book
     * @return
     */
    @Override
    public Book update(Book book) {
        return null;
    }

    /**
     * deleteById: DELETE FROM books WHERE id = ?, поверни true якщо affectedRows > 0.
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
