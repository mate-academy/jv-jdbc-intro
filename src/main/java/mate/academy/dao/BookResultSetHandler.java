package mate.academy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import mate.academy.model.Book;

public interface BookResultSetHandler {

    Long handleGeneratedId(ResultSet generatedKeys) throws SQLException;

    Book handleBookResultSet(ResultSet resultSet) throws SQLException;

}
