package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // CREATE - Add a new book
        System.out.println("=== CREATE ===");
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(100));
        book.setTitle("Tom Sawyer");
        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook.getId() + " - "
                + createdBook.getTitle() + " - $" + createdBook.getPrice());

        // CREATE - Add another book
        Book book2 = new Book();
        book2.setTitle("Harry Potter");
        book2.setPrice(BigDecimal.valueOf(200));
        bookDao.create(book2);

        Book book3 = new Book();
        book3.setTitle("The Hobbit");
        book3.setPrice(BigDecimal.valueOf(150));
        bookDao.create(book3);

        // FIND ALL - Get all books
        System.out.println("\n=== FIND ALL ===");
        List<Book> books = bookDao.findAll();
        books.forEach(b -> System.out.println(b.getId() + " - "
                + b.getTitle() + " - $" + b.getPrice()));

        // FIND BY ID - Get a specific book
        System.out.println("\n=== FIND BY ID ===");
        Optional<Book> optionalFirstBook = bookDao.findById(1L);
        if (optionalFirstBook.isPresent()) {
            Book firstBook = optionalFirstBook.get();
            System.out.println("Found book: " + firstBook.getId() + " - "
                    + firstBook.getTitle() + " - $" + firstBook.getPrice());

            // UPDATE - Modify the book
            System.out.println("\n=== UPDATE ===");
            firstBook.setTitle("Tom Sawyer - Updated Edition");
            firstBook.setPrice(BigDecimal.valueOf(120));
            Book updatedBook = bookDao.update(firstBook);
            System.out.println("Updated book: " + updatedBook.getId() + " - "
                    + updatedBook.getTitle() + " - $" + updatedBook.getPrice());
        } else {
            System.out.println("Book with id 1 not found");
        }

        // FIND ALL - Verify update
        System.out.println("\n=== FIND ALL (after update) ===");
        books = bookDao.findAll();
        books.forEach(b -> System.out.println(b.getId() + " - "
                + b.getTitle() + " - $" + b.getPrice()));

        // DELETE - Remove a book
        System.out.println("\n=== DELETE ===");
        boolean isDeleted = bookDao.deleteById(2L);
        System.out.println("Book with id 2 deleted: " + isDeleted);

        // FIND ALL - Verify deletion
        System.out.println("\n=== FIND ALL (after delete) ===");
        books = bookDao.findAll();
        books.forEach(b -> System.out.println(b.getId() + " - "
                + b.getTitle() + " - $" + b.getPrice()));

        // Try to find deleted book
        System.out.println("\n=== FIND DELETED BOOK ===");
        Optional<Book> deletedBook = bookDao.findById(2L);
        System.out.println("Book with id 2 exists: " + deletedBook.isPresent());
    }
}
