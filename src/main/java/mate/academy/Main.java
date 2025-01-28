package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.model.BookDao;

public class Main {
    private static final Injector injector = Injector.getInstance("book");

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
