package mate.academy.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
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
public class BookDaoImp implements BookDao {
    private static final String FIND_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM books";
    private static final String DELETE_BY_ID = "DELETE FROM books WHERE id = ?";
    private static final String ADD_TO_BOOK = "INSERT INTO books (title, price) VALUES (?, ?)";
    private static final String UPDATE_IN_BOOK = "UPDATE books SET Price = ? WHERE title = ?";
    private static final String PATH_TO_FILE = "src/main/resources/init_db.sql";
    private static final String COLUM_LABEL_TITLE = "title";
    private static final String COLUM_LABEL_PRICE = "price";
    private static final String COLUM_LABEL_ID = "id";

    @Override
    public Book create(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(ADD_TO_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, book.getTitle());
            statement.setBigDecimal(2, book.getPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows < 1) {
                throw new DataProcessingException("Can't create new row "
                        + "affected rows was = " + affectedRows);
            }
            ResultSet resultKey = statement.getGeneratedKeys();
            if (resultKey.next()) {
                book.setId(resultKey.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create new row for book - " + book, e);
        }
        return book;
    }

    @Override
    public Optional<Book> findById(Long id) {
        Book book = null;
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(FIND_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                book = initializeBook(resultSet);
            }
            return Optional.ofNullable(book);
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find data by id - " + id, e);
        }
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(FIND_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                books.add(initializeBook(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't findAll data", e);
        }
        return books;
    }

    private Book initializeBook(ResultSet resultSet) {
        try {
            String title = resultSet.getString(COLUM_LABEL_TITLE);
            BigDecimal price = resultSet.getBigDecimal(COLUM_LABEL_PRICE);
            Long id = resultSet.getObject(COLUM_LABEL_ID,Long.class);
            Book book = new Book();
            book.setPrice(price);
            book.setTitle(title);
            book.setId(id);
            return book;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't initialize book", e);
        }
    }

    @Override
    public Book update(Book book) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(UPDATE_IN_BOOK, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(1, book.getPrice());
            statement.setString(2, book.getTitle());
            statement.executeUpdate();
            ResultSet resultKey = statement.getGeneratedKeys();
            if (resultKey.next()) {
                book.setId(resultKey.getObject(COLUM_LABEL_ID, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update data book - " + book, e);
        }
        return book;
    }

    @Override
    public boolean deleteById(Long id) {
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(DELETE_BY_ID)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete data by id - " + id, e);
        }
    }

    public boolean createBooksTable() {
        StringBuilder sqlQuery = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_FILE))) {
            String line;
            while (true) {
                if ((line = reader.readLine()) == null) {
                    break;
                }
                sqlQuery.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new DataProcessingException("Can't read SQL script", e);
        }
        try (PreparedStatement statement = ConnectionUtil.getConnection()
                .prepareStatement(sqlQuery.toString())) {
            statement.execute();
        } catch (Exception e) {
            throw new DataProcessingException("Can't create books table", e);
        }
        return true;
    }
}
