package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Create and save new book
        Book book = new Book();
        book.setTitle("Clean code");
        book.setPrice(BigDecimal.valueOf(50.0));
        bookDao.create(book);

        // Find all books and print them
        bookDao.findAll().forEach(System.out::println);

        // Find book by id if exists and print it
        bookDao.findById(book.getId()).ifPresent(System.out::println);

        // Update book
        book.setPrice(BigDecimal.valueOf(100.0));
        System.out.println(bookDao.update(book));

        // Find book by
        System.out.println("Is book deleted?: " + bookDao.deleteById(book.getId()));
    }
}
