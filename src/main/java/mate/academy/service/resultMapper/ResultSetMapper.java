package mate.academy.service.resultMapper;

import mate.academy.model.Book;

import java.sql.ResultSet;

public interface ResultSetMapper {
    Book mapFromResultToBook(ResultSet resultSet);
}
