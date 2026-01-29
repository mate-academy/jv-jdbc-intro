package mate.academy;

import mate.academy.dao.BookDao;
import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Clean Code", new BigDecimal("39.99"));
        bookDao.create(book);

        bookDao.findById(book.getId()).ifPresent(System.out::println);

        book.setPrice(new BigDecimal("29.99"));
        bookDao.update(book);

        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(book.getId());
    }
}
