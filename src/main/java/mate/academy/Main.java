package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String PACKAGE_NAME = "mate.academy";

    public static void main(String[] args) {
        try {
            BookDao bookDao;
            bookDao = (BookDao) Injector.getInstance(PACKAGE_NAME).getInstance(BookDao.class);

            Book newBook = new Book();
            newBook.setTitle("Introduction to Java");
            newBook.setPrice(new BigDecimal("29.99"));
            bookDao.create(newBook);

            Optional<Book> foundBook = bookDao.findById(newBook.getId());
            foundBook.ifPresent(book -> logger.info("Found book: {}", book));

            if (foundBook.isPresent()) {
                Book bookToUpdate = foundBook.get();
                bookToUpdate.setTitle("Updated Java Introduction");
                bookToUpdate.setPrice(new BigDecimal("39.99"));
                bookDao.update(bookToUpdate);
            }

            List<Book> allBooks = bookDao.findAll();
            logger.info("All books in the database:");
            allBooks.forEach(book -> logger.info(book.toString()));

            if (foundBook.isPresent()) {
                boolean deleted = bookDao.deleteById(foundBook.get().getId());
                logger.info("Book deletion status: {}", (deleted ? "Success" : "Failed"));
            }
        } catch (Exception e) {
            logger.error("An error occurred in the application", e);
        }
    }
}
