package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.ConnectionUtil;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Head First Java by Kathy Sierra & Bert Bates");
        book.setPrice(BigDecimal.valueOf(38.50));

        try {
            ConnectionUtil.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        bookDao.create(book);

        bookDao.findById(9L);

        bookDao.findAll();

        book.setPrice(BigDecimal.valueOf(39.50));
        book.setId(9L);
        bookDao.update(book);

        bookDao.deleteById(9L);

    }
}
