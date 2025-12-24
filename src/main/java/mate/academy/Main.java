package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance(
            "mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPrice(BigDecimal.valueOf(300));
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPrice(BigDecimal.valueOf(700));
        bookDao.create(book2);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setPrice(BigDecimal.valueOf(1200));
        bookDao.create(book3);

        bookDao.findAll();
        bookDao.findById(2L);
        book1.setTitle("Updated Book 1");
        book1.setPrice(BigDecimal.valueOf(455));
        bookDao.update(book1);
        bookDao.deleteById(3L);
    }
}
