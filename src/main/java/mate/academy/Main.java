package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // initialize field values using setters or constructor
        Book bookFirst = new Book();
        bookFirst.setTitle("Java 8");
        bookFirst.setPrice(BigDecimal.valueOf(850));

        Book bookSecond = new Book();
        bookSecond.setTitle("Java 9");
        bookSecond.setPrice(BigDecimal.valueOf(1050));

        // test other methods from BookDao
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println(bookDao.create(bookFirst));
        System.out.println(bookDao.create(bookSecond));

        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findById(2L));

        System.out.println(bookDao.findAll());

        bookSecond.setId(1L);
        bookDao.update(bookSecond);
        System.out.println(bookDao.findById(1L));
        bookFirst.setId(1L);
        bookDao.update(bookFirst);
        System.out.println(bookDao.findById(1L));

        System.out.println(bookDao.deleteById(2L));
        System.out.println(bookDao.findAll());
    }
}
