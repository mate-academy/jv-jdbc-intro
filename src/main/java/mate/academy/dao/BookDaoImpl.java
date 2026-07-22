package mate.academy.dao;

import mate.academy.ConnectionUtil;
import mate.academy.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
             PreparedStatement statement
                     = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new RuntimeException("didn't make changes in any rows");
            }
            ResultSet generatedKeys = statement.getGeneratedKeys(); // хоть це і statement.executeUpdate(),
            // проте в обєкта statement викликаєм метод .getGeneratedKeys() що повертає обєкт ResultSet
            // знаючи що з ResultSet можна отримати доступ до стовпців (отримуємо достур до стопвця id), ми присвоюєм щойно згенерований id для обєкта в java
            if (generatedKeys.next()) {
                Long id = generatedKeys.getObject(1, Long.class);
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can not add new book", e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String sql = "SELECT * FROM books WHERE id = ?";    // 1-ий етарп

        try(Connection connection = ConnectionUtil.getConnection();     // робим конекшн (який попередньо реалізували в класі ConnectionUtil)
            PreparedStatement statement = connection.prepareStatement(sql)) {   // репрезентація запиту

            statement.setLong(1, id);  // вказуємо id для якого проведемо CRUD-операцію, (id яке на вхід в метод)
            ResultSet resultSet = statement.executeQuery();   // репрезентація відповіді

            if (resultSet.next()) {        // без перевірки буде вийняток
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);

                Book book= new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);

                return Optional.of(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can not add new book", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT * FROM books";    // 1-ий етарп

        try(Connection connection = ConnectionUtil.getConnection();     // робим конекшн (який попередньо реалізували в класі ConnectionUtil)
            PreparedStatement statement = connection.prepareStatement(sql)) {   // репрезентація запиту

            ResultSet resultSet = statement.executeQuery();   // репрезентація відповіді

            while (resultSet.next()) {        // без перевірки буде вийняток
                String title = resultSet.getString("title");
                BigDecimal price = resultSet.getObject("price", BigDecimal.class);
                Long id = resultSet.getObject("id", Long.class);

                Book book= new Book();
                book.setId(id);
                book.setTitle(title);
                book.setPrice(price);

                bookList.add(book);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can not add new book", e);
        }
        return bookList;
    }

    @Override
    public Book update(Book book) {
        String sql = "UPDATE books SET title = ?, price = ? WHERE id = ?";    // 1-ий етарп

        try(Connection connection = ConnectionUtil.getConnection();     // робим конекшн (який попередньо реалізували в класі ConnectionUtil)
            PreparedStatement statement = connection.prepareStatement(sql)) {   // репрезентація запиту

            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3, book.getId());

            int affectedRows = statement.executeUpdate();  // (1 - рядок був змін, 0 - жодних рядків не змін)
            if (affectedRows < 1) {
                throw new RuntimeException("didn't make changes in any rows");
            }
        } catch (SQLException e) {
            throw new DataProcessingException("can not add new book", e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        String sql = "DELETE FROM books WHERE id = ?";    // 1-ий етарп

        try(Connection connection = ConnectionUtil.getConnection();     // робим конекшн (який попередньо реалізували в класі ConnectionUtil)
            PreparedStatement statement = connection.prepareStatement(sql)) {   // репрезентація запиту

            statement.setLong(1, id);  // вказуємо id для якого проведемо CRUD-операцію, (id яке на вхід в метод)
            int affectedRows = statement.executeUpdate();  // (1 - рядок був змін, 0 - жодних рядків не змін)
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("can not add new book", e);
        }
    }
}
