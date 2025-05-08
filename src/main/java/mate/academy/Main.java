package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final Long BOOK_ID = 3L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = createSampleBooks();

        //Add books to the database.
        for (Book book : books) {
            try {
                bookDao.create(book);
            } catch (DataProcessingException e) {
                logger.severe("DataProcessingException occurred while creating a book: "
                        + book + " - " + e.getMessage());
            } catch (Exception e) {
                logger.severe("Unexpected error occurred while creating book: "
                        + book + " - " + e.getMessage());
            }
        }
        //Find a book by ID.
        try {
            Optional<Book> daoById = bookDao.findById(BOOK_ID);
            logger.info("Found book by ID: " + daoById);
        } catch (DataProcessingException e) {
            logger.severe("DataProcessingException occurred while finding book by ID: "
                    + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error occurred while finding book by ID: " + e.getMessage());
        }

        //Find all books.

        try {
            List<Book> allBooks = bookDao.findAll();
            logger.info("All books: " + allBooks);
        } catch (DataProcessingException e) {
            logger.severe("DataProcessingException occurred while finding all books: "
                    + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error occurred while finding all books: " + e.getMessage());
        }

        //Update the book.
        books.get(0).setTitle("Harry Potter 2");
        books.get(0).setPrice(new BigDecimal(50));

        try {
            bookDao.update(books.get(0));
        } catch (DataProcessingException e) {
            logger.severe("DataProcessingException occurred while updating book: "
                    + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error occurred while updating book: " + e.getMessage());
        }

        //Delete the book.
        try {
            bookDao.deleteById(BOOK_ID);
        } catch (DataProcessingException e) {
            logger.severe("DataProcessingException occurred while deleting book by ID: "
                    + e.getMessage());
        } catch (Exception e) {
            logger.severe("Unexpected error occurred while deleting book by ID: "
                    + e.getMessage());
        }
    }

    private static List<Book> createSampleBooks() {
        return List.of(
                new Book(1L, "Harry Potter", new BigDecimal(40)),
                new Book(2L, "The Great Gatsby", new BigDecimal(15)),
                new Book(3L, "To Kill a Mockingbird", new BigDecimal(20)),
                new Book(4L, "1984", new BigDecimal(25)),
                new Book(5L, "The Catcher in the Rye", new BigDecimal(30))
        );
    }
}
