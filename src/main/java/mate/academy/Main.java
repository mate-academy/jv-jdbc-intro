package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Create a book
        Book book = new Book();
        book.setTitle("Java Basics");
        book.setPrice(BigDecimal.valueOf(29.99));
        bookDao.create(book);

        // Find by ID
        System.out.println(bookDao.findById(book.getId()));

        // Update book
        book.setPrice(BigDecimal.valueOf(35.99));
        bookDao.update(book);

        // Find all books
        System.out.println(bookDao.findAll());

        // Delete by ID
        bookDao.deleteById(book.getId());
    }
}
