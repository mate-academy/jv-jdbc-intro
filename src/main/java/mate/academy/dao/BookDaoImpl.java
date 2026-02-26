package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    private static final Injector injector = Injector.getInstance("mate.academy");

    /**
     * create: INSERT ... RETURN_GENERATED_KEYS, встанови id згенерований.
     * @param book entity
     * @return Book
     */
    @Override
    public Book create(Book book) {
        return null;
    }

    /**
     * findById: SELECT ... WHERE id = ?, якщо запис знайдений — поверни Optional.of(book),
     * інакше Optional.empty().
     * @param id entity
     * @return Optional
     */
    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection(); PreparedStatement statement
                = connection.prepareStatement(sql);) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
                Book book = new Book();
                bookDao.create(book);

                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");

                book.setId((Long) id);
                book.setTitle(title);
                book.setPrice(BigDecimal.valueOf(price));

                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    /**
     * findAll: SELECT * FROM books, пробіжи ResultSet, збирай у список.
     * @return List
     */
    @Override
    public List<Book> findAll() {
        return List.of();
    }

    /**
     * update: UPDATE books SET title = ?, price = ? WHERE id = ?, поверни оновлений об’єкт
     * або кинь DataProcessingException при помилці.
     * @param book entity
     * @return Book
     */
    @Override
    public Book update(Book book) {
        return null;
    }

    /**
     * deleteById: DELETE FROM books WHERE id = ?, поверни true якщо affectedRows > 0.
     * @param id entity
     * @return boolean
     */
    @Override
    public boolean deleteById(Long id) {
        return false;
    }
}
