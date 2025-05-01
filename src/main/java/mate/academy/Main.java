package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String TITLE = "test_title";
    private static final BigDecimal PRICE = BigDecimal.valueOf(99.90);
    private static final BigDecimal UPDATE_PRICE = BigDecimal.valueOf(109.90);

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle(TITLE);
        book.setPrice(PRICE);

        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        Optional<Book> findByIdBook = bookDao.findById(createdBook.getId());
        System.out.println("Found book by id: " + findByIdBook);

        book.setPrice(UPDATE_PRICE);
        Book updatedBook = bookDao.update(book);
        System.out.println("Updated book: " + updatedBook);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("There are " + allBooks.size() + " book stored in DB");

        boolean isBookSuccessfullyDeleted = bookDao.deleteById(book.getId());
        System.out.println("Delete book from DB.Result: " + isBookSuccessfullyDeleted);
    }
}
