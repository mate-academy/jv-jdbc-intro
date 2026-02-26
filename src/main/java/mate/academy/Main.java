package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * У main отримай BookDao через injector і протестуй всі CRUD-методи:
 * create → findById → findAll → update → deleteById.
 */
public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        // initialize field values using setters or constructor
        bookDao.create(book);
        // test other methods from BookDao

        //bookDao.create(new Book());
        Connection connection = ConnectionUtil.getConnection();
        System.out.println(connection);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
