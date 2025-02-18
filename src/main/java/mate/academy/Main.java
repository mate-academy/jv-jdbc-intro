package mate.academy;

import java.math.BigDecimal;
import mate.academy.be.Book;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Harry Potter", new BigDecimal("100"));
        bookDao.create(book);

        System.out.println(bookDao.findAll());
    }
}
