package mate.academy;

import mate.academy.util.ConnectionUtil;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book greatGatsby = new Book("Great Gatsby", new BigDecimal("45.50"));
        greatGatsby = bookDao.create(greatGatsby);
        System.out.println(greatGatsby);
        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.findById(greatGatsby.getId()).toString());
        System.out.println(bookDao.deleteById(greatGatsby.getId()));
    }
}
