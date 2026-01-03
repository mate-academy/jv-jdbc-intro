package mate.academy;

import java.sql.SQLException;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.db.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws SQLException {
        ConnectionUtil.getConnection();
        BookDao test = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = test.findAll();
        for (Book b : books) {
            System.out.println(b);
        }
        System.out.println(test.findById(5L));
        test.deleteById(5L);
        books = test.findAll();
        for (Book b : books) {
            System.out.println(b);
        }
    }
}
