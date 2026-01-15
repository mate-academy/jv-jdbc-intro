package mate.academy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.ConnectionUtil;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;

@Dao
public class BookDaoImpl implements BookDao {
    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO books (title, price) VALUES (?,?)";
        // створюємо SQL-запит, який ми будемо передавати в БД
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            //вказуємо, які самі дані нам підставляти замість ?
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            // ми сформували statement, тепер потрібно його виконати
            int affectedRows = statement.executeUpdate(); // кількість рядків, які ми додали
            if (affectedRows < 1) {
                throw new RuntimeException("Expected to insert at leas one rows,"
                        + " but inserted 0 rows");
            }
            // отримаємо з statement ідентифікатор, який згенерувався в базі даних
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                // тоді отримаємо ідентифікатор
                Long id = generatedKeys.getObject(1, Long.class);
                // додаємо/сеттеремо цей id в book
                book.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can not add a new book " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            // в ResultSet ми отримаємо інформацію, яка мені повернеться з БД
            // інтерфейс PreparedStatement  розширяє інтерфейс Statement. PreparedStatement
            // і Statement використовується, для того, щоб ми могли представити певний обєкт
            // запиту до БД. При формуванні ми повинні вказати sql-запит, який будумо
            // передавати на виконання в СУБД. СУБД виконує цей запит і повертає відповідь.
            // ResultSet репрезентує відповід СУБД у вигляді таблички - результат виконання запиту
            // Statement використовується тоді,
            // коли немає динамічно змінювальних параметрів (тут значення id)

            // перевіряємо чи resultSet повепнув якись результат
            if (resultSet.next()) {
                //отримаємо результат з обєкта ResultSet
                // String title = resultSet.getString("title");
                //BigDecimal price = resultSet.getBigDecimal("price");
                //Book book = new Book();
                //book.setId(id);
                //book.setTitle(title);
                // book.setPrice(price);
                //return Optional.of(book);
                return Optional.of(addDataBook(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find any data for id = " + id, e);
        }

        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        String query = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            // Statement statement = connection.createStatement()) {
            //  ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                // Book book = new Book();Long id = resultSet.getObject("id", Long.class);
                // String title = resultSet.getString("title");
                // BigDecimal price = resultSet.getBigDecimal("price");
                // book.setId(id); book.setTitle(title); book.setPrice(price);
                // books.add(book);
                books.add(addDataBook(resultSet));
            }

        } catch (SQLException e) {
            throw new DataProcessingException("Can not find any data from the  table",e);
        }
        return books;
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == null) {
            throw new RuntimeException("Book id is null. Use create() first");
        }
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            statement.setLong(3,book.getId());
            int updatedRows = statement.executeUpdate();
            if (updatedRows < 1) {
                throw new RuntimeException("Can't update book with id = " + book.getId());
            }
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not update data with id = " + book.getId(), e);
        }
    }

    @Override
    public boolean deleteById(Long id) {
        String query = "DELETE FROM books WHERE id = ?";

        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can not delete data with id = " + id,e);
        }
    }

    private Book addDataBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getObject("id", Long.class));
        book.setTitle(resultSet.getString("title"));
        book.setPrice(resultSet.getBigDecimal("price"));
        return book;
    }
}
