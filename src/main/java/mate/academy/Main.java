package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(null, "The Adventure", new BigDecimal("11.99"));
        book = bookDao.create(book);
        System.out.println("Created: " + book);

        bookDao.findAll().forEach(System.out::println);
    }
}
