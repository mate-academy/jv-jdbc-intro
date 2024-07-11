package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Sample Book");
        book.setPrice(BigDecimal.valueOf(29.99));
        book = bookDao.create(book);

        bookDao.findById(book.getId()).ifPresent(System.out::println);

        book.setTitle("Updated Book Title");
        book.setPrice(BigDecimal.valueOf(39.99));
        bookDao.update(book);

        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(book.getId());
    }
}
