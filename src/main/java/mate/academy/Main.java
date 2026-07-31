package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setPrice(new BigDecimal("45.99"));

        Book createdBook = bookDao.create(book);
        System.out.println("Created: " + createdBook);
        System.out.println("Found by id: " + bookDao.findById(createdBook.getId()));
        System.out.println("All books: " + bookDao.findAll());

        createdBook.setTitle("Effective Java, Third Edition");
        createdBook.setPrice(new BigDecimal("49.99"));
        System.out.println("Updated: " + bookDao.update(createdBook));

        System.out.println("Deleted: " + bookDao.deleteById(createdBook.getId()));
    }
}
