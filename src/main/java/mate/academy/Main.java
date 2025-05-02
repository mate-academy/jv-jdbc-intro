package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(null, "Mein Kampf", new BigDecimal("9.99"));
        book = bookDao.create(book);
        System.out.println("Created: " + book);

        bookDao.findAll().forEach(System.out::println);
    }
}
