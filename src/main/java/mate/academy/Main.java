package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Test save
        Book bookToSave = new Book();
        bookToSave.setTitle("New Book");
        bookToSave.setPrice(new BigDecimal("19.99"));

        Book savedBook = bookDao.save(bookToSave);
        System.out.println("Saved Book: " + savedBook);

        // Test findById
        Optional<Book> foundBook = bookDao.findById(savedBook.getId());
        foundBook.ifPresentOrElse(
                book -> System.out.println("Found Book: " + book),
                () -> System.out.println("Book not found.")
        );

        // Test findAll
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books:");
        allBooks.forEach(System.out::println);

        // Test update
        savedBook.setPrice(new BigDecimal("29.99"));
        Book updatedBook = bookDao.update(savedBook);
        System.out.println("Updated Book: " + updatedBook);

        // Test delete
        bookDao.delete(savedBook);
        System.out.println("Deleted Book: " + savedBook);
    }
}
