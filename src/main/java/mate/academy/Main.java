package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;

public class Main {
    private static final BookDao bookDao = new BookDaoImpl();
    private static final BigDecimal FIRST_PRICE = BigDecimal.valueOf(29.99);
    private static final String SECOND_PRICE = "35.99";
    private static final String DEFAULT_BOOK_TITLE = "Java for Beginners";

    public static void main(String[] args) {

        Book newBook = new Book(DEFAULT_BOOK_TITLE,FIRST_PRICE);
        Book savedBook = bookDao.create(newBook);
        System.out.println("Created book: " + savedBook);

        Long bookId = savedBook.getId();
        Optional<Book> foundBook = bookDao.findById(bookId);
        foundBook.ifPresent(book -> System.out.println("Found book: " + book));

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in DB: " + allBooks);

        if (foundBook.isPresent()) {
            Book bookToUpdate = foundBook.get();
            bookToUpdate.setPrice(new BigDecimal(SECOND_PRICE));
            Book updatedBook = bookDao.update(bookToUpdate);
            System.out.println("Updated book: " + updatedBook);
        }

        boolean isDeleted = bookDao.deleteById(bookId);
        if (isDeleted) {
            System.out.println("Book with id " + bookId + " was deleted successfully.");
        } else {
            System.out.println("Book with id " + bookId + " was not found.");
        }
    }
}
