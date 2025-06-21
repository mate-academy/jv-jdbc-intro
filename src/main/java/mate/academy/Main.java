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
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // 1. Create a new book
        Book newBook = new Book();
        newBook.setTitle("Test Book");
        newBook.setPrice(new BigDecimal("15.99"));
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book: " + createdBook);

        // 2. Find book by id
        Optional<Book> foundBook = bookDao.findById(createdBook.getId().intValue());
        foundBook.ifPresentOrElse(
                book -> System.out.println("Found book by id: " + book),
                () -> System.out.println("Book not found by id")
        );

        // 3. Find all books
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in DB:");
        allBooks.forEach(System.out::println);

        // 4. Update the book
        createdBook.setTitle("Updated Title");
        createdBook.setPrice(new BigDecimal("19.99"));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        // 5. Delete the book
        boolean isDeleted = bookDao.deleteById(updatedBook.getId());
        System.out.println("Book deleted: " + isDeleted);

        // 6. Verify deletion
        Optional<Book> afterDelete = bookDao.findById(updatedBook.getId().intValue());
        System.out.println("Find after deletion: " + (afterDelete.isEmpty()
                ? "Not found" : "Found"));
    }
}
