package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("The 48 Laws of Power");
        book.setPrice(new BigDecimal("400.00"));

        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("One piece");
        updatedBook.setPrice(new BigDecimal("600.00"));

        // test methods from BookDao
        bookDao.create(book);
        System.out.println("Created: " + book);

        Optional<Book> foundBook = bookDao.findById(3L);
        foundBook.ifPresent(b -> System.out.println("Found: " + b));

        bookDao.update(updatedBook);
        System.out.println("Updated: " + updatedBook);

        List<Book> books = bookDao.findAll();
        System.out.println("Books: " + books);

        boolean deleted = bookDao.deleteById(2L);
        System.out.println("Deleted:" + deleted);
    }
}
