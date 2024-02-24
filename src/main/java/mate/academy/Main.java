package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.db.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final Book NEW_BOOK_FIRST =
            new Book(null, "Hobbit", BigDecimal.valueOf(49.99));
    private static final Book NEW_BOOK_SECOND =
            new Book(null, "The Odyssey", BigDecimal.valueOf(29.99));
    private static final String NEW_TITLE = "Lord of the Rings: The Two Towers";
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(149.99);

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        // CREATE
        System.out.println("Added: " + bookDao.create(NEW_BOOK_FIRST));
        System.out.println("Added: " + bookDao.create(NEW_BOOK_SECOND));

        // READ
        Optional<Book> bookById = bookDao.findById(2L);
        System.out.println("Got by ID 2: " + bookById.get());
        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: ");
        allBooks.forEach(System.out::println);

        // UPDATE
        Book bookToUpdate = bookDao.findById(1L).get();
        System.out.println("Book before update: " + bookToUpdate);
        bookToUpdate.setTitle(NEW_TITLE);
        bookToUpdate.setPrice(NEW_PRICE);
        Book bookAfterUpdate = bookDao.update(bookToUpdate);
        System.out.println("Book after update: " + bookAfterUpdate);

        // DELETE
        boolean isDeleted = bookDao.deleteById(2L);
        System.out.println("Deleted book by ID 2: " + isDeleted);
    }
}
