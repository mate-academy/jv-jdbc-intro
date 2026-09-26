package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // 1. Create
        Book book = new Book();
        book.setTitle("Thinking in Java");
        book.setPrice(new BigDecimal("49.99"));
        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        // 2. Find by ID
        bookDao.findById(createdBook.getId())
                .ifPresent(foundBook -> System.out.println("Found book: " + foundBook));

        // 3. Update
        createdBook.setTitle("Thinking in Java (4th Edition)");
        createdBook.setPrice(new BigDecimal("55.00"));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        // 4. Find All
        System.out.println("All books in DB: " + bookDao.findAll());

        // 5. Delete
        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Book deleted status: " + isDeleted);
    }
}
