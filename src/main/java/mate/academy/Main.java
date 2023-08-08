package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Dark", new BigDecimal("17"));

        Book darkBook = bookDao.create(new Book("Dark 2", new BigDecimal("25")));

        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.update(darkBook));
        System.out.println(bookDao.findAll());
    }
}
