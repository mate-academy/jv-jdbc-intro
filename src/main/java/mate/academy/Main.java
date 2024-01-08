package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book();
        newBook.setTitle("Effective Java");
        newBook.setPrice(BigDecimal.valueOf(45.99));
        System.out.println("Creating book: " + newBook);
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book: " + createdBook);

        Long bookId = createdBook.getId();
        Optional<Book> retrievedBook = bookDao.findById(bookId);
        retrievedBook.ifPresent(book -> System.out.println("Retrieved book by ID: " + book));

        createdBook.setPrice(BigDecimal.valueOf(39.99));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);

        boolean isDeleted = bookDao.deleteById(bookId);
        System.out.println("Book deleted: " + isDeleted);
    }
}
