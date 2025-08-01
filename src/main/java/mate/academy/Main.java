package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book();
        newBook.setTitle("Clean Code");
        newBook.setPrice(new BigDecimal("450.50"));
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        if (foundBook.isPresent()) {
            System.out.println("Found book by ID: " + foundBook.get());
        } else {
            System.out.println("Book with ID " + createdBook.getId() + " not found.");
        }

        createdBook.setTitle("Clean Code (Updated)");
        createdBook.setPrice(new BigDecimal("499.99"));
        try {
            Book updatedBook = bookDao.update(createdBook);
            System.out.println("Updated book: " + updatedBook);
        } catch (DataProcessingException e) {
            System.out.println("Update failed: " + e.getMessage());
        }

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in DB:");
        allBooks.forEach(System.out::println);

        boolean deleted = bookDao.deleteById(createdBook.getId());
        System.out.println(deleted
                ? "Deleted book with ID " + createdBook.getId()
                : "No book found with ID " + createdBook.getId());

        Book fakeBook = new Book();
        fakeBook.setTitle("Ghost Book");
        fakeBook.setPrice(new BigDecimal("123.45"));
        try {
            Book updatedGhost = bookDao.update(fakeBook);
            System.out.println("Updated ghost book: " + updatedGhost);
        } catch (DataProcessingException e) {
            System.out.println("Failed to update non-existing book: " + e.getMessage());
        }
    }
}
