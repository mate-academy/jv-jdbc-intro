package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance("mate.academy");

        Book book = new Book();
        book.setTitle("Effective Java");
        book.setPrice(BigDecimal.valueOf(49.99));

        System.out.println("=== Creating a book ===");
        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        System.out.println("=== Finding a book by ID ===");
        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        foundBook.ifPresentOrElse(
                (presentBook) -> System.out.println("Found book: " + presentBook),
                () -> System.out.println("Book not found!")
        );

        System.out.println("=== Updating the book ===");
        createdBook.setTitle("Effective Java - Updated");
        createdBook.setPrice(BigDecimal.valueOf(59.99));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        System.out.println("=== Retrieving all books ===");
        bookDao.findAll().forEach(System.out::println);

        System.out.println("=== Deleting the book by ID ===");
        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Was the book deleted? " + isDeleted);

        System.out.println("=== Searching for the book again after deletion ===");
        foundBook = bookDao.findById(createdBook.getId());
        foundBook.ifPresentOrElse(
                (presentBook) -> System.out.println("Found book: " + presentBook),
                () -> System.out.println("Book not found!")
        );

        System.out.println("=== Program finished ===");
    }
}