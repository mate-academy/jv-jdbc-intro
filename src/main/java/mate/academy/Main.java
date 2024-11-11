package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book newBook = new Book(null, "Effective Java", new BigDecimal("39.99"));
        bookDao.create(newBook);

        // Find a book by ID
        Optional<Book> foundBook = bookDao.findById(newBook.getId());
        foundBook.ifPresent(book -> System.out.println("Found book: " + book.getTitle()));

        // Update book
        newBook.setPrice(new BigDecimal("34.99"));
        bookDao.update(newBook);

        // Find all books
        bookDao.findAll().forEach(book -> System.out.println(book.getTitle()));

        // Delete book
        bookDao.deleteById(newBook.getId());
    }
}
