package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(45.99));

        bookDao.create(book);

        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());

        book.setTitle("Effective Java");
        bookDao.update(book);

        System.out.println(bookDao.findById(book.getId()));

        System.out.println(bookDao.deleteById(book.getId()));
    }
}
