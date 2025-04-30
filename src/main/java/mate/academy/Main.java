package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.lib");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setPrice(BigDecimal.valueOf(45.99));

        bookDao.create(book);
        System.out.println("Created: " + book);

        bookDao.findAll().forEach(System.out::println);

        book.setTitle("Effective Java 3rd Edition");
        bookDao.update(book);
        System.out.println("Updated: " + book);

        bookDao.deleteById(book.getId());
        System.out.println("Deleted book with ID: " + book.getId());
    }
}
