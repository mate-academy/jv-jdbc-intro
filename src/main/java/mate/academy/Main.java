package mate.academy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mate.academy.dao.BookDao;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

/**
 * У main отримай BookDao через injector і протестуй всі CRUD-методи:
 * create → findById → findAll → update → deleteById.
 */
public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws SQLException {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        // initialize field values using setters or constructor
        bookDao.create(book);
        // test other methods from BookDao

        //bookDao.create(new Book());
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM books";
        PreparedStatement statement = connection.prepareStatement(sql);

        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            Object id = resultSet.getObject("id");
            String title = resultSet.getString("title");
            double price = resultSet.getDouble("price");
            System.out.print(id + " " + title + " " + price);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
