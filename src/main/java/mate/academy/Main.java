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

        // Test CREATE
        System.out.println("=== Testing CREATE ===");
        Book book1 = new Book("Kobzar", new BigDecimal("250.50"));
        Book book2 = new Book("Clean Code", new BigDecimal("599.99"));
        Book book3 = new Book("Effective Java", new BigDecimal("750.00"));

        Book createdBook1 = bookDao.create(book1);
        Book createdBook2 = bookDao.create(book2);
        Book createdBook3 = bookDao.create(book3);

        System.out.println("Created: " + createdBook1);
        System.out.println("Created: " + createdBook2);
        System.out.println("Created: " + createdBook3);

        // Test FIND BY ID
        System.out.println("\n=== Testing FIND BY ID ===");
        Long searchId = createdBook1.getId();
        Optional<Book> foundBook = bookDao.findById(searchId);
        foundBook.ifPresentOrElse(
                book -> System.out.println("Found book with id " + searchId + ": " + book),
                () -> System.out.println("Book with id " + searchId + " not found")
        );

        // Test finding non-existent book
        Optional<Book> notFoundBook = bookDao.findById(999L);
        System.out.println("Book with id 999: "
                + notFoundBook.map(Book::toString).orElse("Not found"));

        // Test FIND ALL
        System.out.println("\n=== Testing FIND ALL ===");
        List<Book> allBooks = bookDao.findAll();
        System.out.println("Total books: " + allBooks.size());
        allBooks.forEach(System.out::println);

        // Test UPDATE
        System.out.println("\n=== Testing UPDATE ===");
        createdBook1.setTitle("Kobzar (Updated Edition)");
        createdBook1.setPrice(new BigDecimal("299.99"));
        Book updatedBook = bookDao.update(createdBook1);
        System.out.println("Updated book: " + updatedBook);

        // Verify update
        Optional<Book> verifyUpdate = bookDao.findById(updatedBook.getId());
        verifyUpdate.ifPresent(book -> System.out.println("Verified update: " + book));

        // Test DELETE BY ID
        System.out.println("\n=== Testing DELETE BY ID ===");
        Long deleteId = createdBook2.getId();
        boolean isDeleted = bookDao.deleteById(deleteId);
        System.out.println("Book with id " + deleteId + " deleted: " + isDeleted);

        // Verify deletion
        Optional<Book> deletedBook = bookDao.findById(deleteId);
        System.out.println("Verify deletion (should be empty): "
                + (deletedBook.isEmpty() ? "Successfully deleted" : "Still exists"));

        // Show remaining books
        System.out.println("\n=== Remaining books ===");
        List<Book> remainingBooks = bookDao.findAll();
        System.out.println("Total books after deletion: " + remainingBooks.size());
        remainingBooks.forEach(System.out::println);
    }
}
