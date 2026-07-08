package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // CREATE
        Book book = new Book("Harry Potter", BigDecimal.valueOf(450));
        book = bookDao.create(book);
        System.out.println("Created: " + book);

        // FIND BY ID
        System.out.println("Found: " + bookDao.findById(book.getId()));

        // FIND ALL
        System.out.println("All books: " + bookDao.findAll());

        // UPDATE
        book.setTitle("Harry Potter and the Philosopher's Stone");
        book.setPrice(BigDecimal.valueOf(500));
        bookDao.update(book);
        System.out.println("Updated: " + bookDao.findById(book.getId()));

        // DELETE
        System.out.println("Deleted: " + bookDao.deleteById(book.getId()));

        // CHECK DELETE
        System.out.println("After delete: " + bookDao.findById(book.getId()));
    }
}