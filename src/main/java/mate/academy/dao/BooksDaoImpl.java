package mate.academy.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

@Dao
public class BooksDaoImpl implements BooksDao{
    @Override
    public Book create(Book book) {
        String query = "INSERT INTO books(title, price) VALUES(?, ?);";
        try ( Connection connection = ConnectionUtil.getConnection();
              PreparedStatement preparedStatement =
                      connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS ) ) {

            preparedStatement.setString( 1, book.getTitle() );
            preparedStatement.setBigDecimal( 2, book.getPrice() );

            if ( preparedStatement.executeUpdate() == 0 ) {
                throw new RuntimeException( "Expected 1 inserted row but is 0 inserted" );
            }

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if ( generatedKeys.next() ) {
                Long id = generatedKeys.getObject( 1, Long.class );
                book.setId( id );
            }
        } catch ( SQLException e ) {
            throw new DataProcessingException("Cannot insert new value", e );
        }
        return book;
    }

    @Override
    public Optional<Book> findById( Long id ) {
        String query = "SELECT * FROM books WHERE id = ?;";
        try ( Connection connection = ConnectionUtil.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement(query) ) {
            preparedStatement.setLong( 1, id );
            if ( preparedStatement.execute() ) {
                ResultSet resultSet = preparedStatement.getResultSet();
                if ( resultSet.next() ) {
                    return Optional.of( getBookFromResult( resultSet ) );
                }
            }
        } catch ( SQLException e ) {
            throw new DataProcessingException("Cannot find book by id: " + id, e );
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM books;";
        try ( Connection connection = ConnectionUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query) ) {
            if ( preparedStatement.execute() ) {
                ResultSet resultSet = preparedStatement.getResultSet();
                while ( resultSet.next() ) {
                    bookList.add( getBookFromResult( resultSet ) );
                }
            }
        } catch ( SQLException e ) {
            throw new DataProcessingException( "Cannot get all values from DB", e );
        }
        return bookList;
    }

    @Override
    public Book update( Book book ) {
        String query = "UPDATE books SET title = ?, price = ? WHERE id = ?;";
        try ( Connection connection = ConnectionUtil.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement( query ) ) {
            preparedStatement.setString( 1, book.getTitle() );
            preparedStatement.setBigDecimal( 2, book.getPrice() );
            preparedStatement.setLong( 3, book.getId() );
            if (preparedStatement.executeUpdate() > 0 ) {
                return book;
            }
        } catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
        return null;
    }

    @Override
    public boolean deleteById( Long id ) {
        String query = "DELETE FROM books WHERE id = ?;";
        try ( Connection connection = ConnectionUtil.getConnection();
              PreparedStatement preparedStatement = connection.prepareStatement( query ) ) {
            preparedStatement.setLong( 1, id );
            return preparedStatement.executeUpdate() > 0;
        } catch ( SQLException e ) {
            throw new DataProcessingException("Cannot delete value by id: " + id, e );
        }
    }

    private Book getBookFromResult(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getObject( 1, Long.class );
        String title = resultSet.getObject( 2, String.class );
        BigDecimal price = resultSet.getObject( 3, BigDecimal.class );
        return new Book(id, title, price);
    }
}
