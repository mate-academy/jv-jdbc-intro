package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String BOOK_NAME = "Harry Potter";
    private static final String BOOK_NAME_TO_UPDATE = "The Lord of The Rings";
    private static final BigDecimal BOOK_PRICE = new BigDecimal(500);
    private static final BigDecimal BOOK_PRICE_TO_UPDATE = new BigDecimal(1000);

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Create (add to DB) new book
        Book book = new Book();
        book.setTitle(BOOK_NAME);
        book.setPrice(BOOK_PRICE);
        Book newBook = bookDao.create(book);
        System.out.println(newBook);

        // Find book by id
        Optional<Book> bookById = bookDao.findById(1L);
        if (bookById.isPresent()) {
            Book book1 = bookById.get();
            System.out.println(book1);
        }

        // Find all books
        List<Book> findAllBooks = bookDao.findAll();
        System.out.println(findAllBooks);

        // Update book
        Book bookToUpdate = new Book();
        bookToUpdate.setId(2L);
        bookToUpdate.setTitle(BOOK_NAME_TO_UPDATE);
        bookToUpdate.setPrice(BOOK_PRICE_TO_UPDATE);
        Book updatedBook = bookDao.update(bookToUpdate);
        System.out.println(updatedBook);

        // Delete book by id
        boolean isBookDeleted = bookDao.deleteById(6L);
        System.out.println(isBookDeleted);
    }
}
