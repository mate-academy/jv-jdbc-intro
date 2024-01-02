package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Design patterns");
        book1.setPrice(new BigDecimal(49.5));
        Book bookDP = bookDao.create(book1);
        Book book2 = new Book();
        book2.setTitle("Java for dummies");
        book2.setPrice(new BigDecimal(150));
        Book bookJD = bookDao.create(book2);

        System.out.println(bookDao.findById(1L));
        bookDao.findAll().forEach(System.out::println);
    }
}
