package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.BookDao;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        //Create book
        Book book = new Book();
        book.setTitle("Java Programming for Kids");
        book.setPrice(new BigDecimal("220.50"));
        bookDao.create(book);

        // Find by ID
        bookDao.findById(book.getId()).ifPresent(System.out::println);

        // Update book
        book.setTitle("Java Programming");
        book.setPrice(new BigDecimal("490.40"));
        bookDao.update(book);

        // Find all books
        bookDao.findAll().forEach(System.out::println);

        // Delete book
        bookDao.deleteById(book.getId());
    }
}
