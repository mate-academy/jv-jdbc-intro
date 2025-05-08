package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BigDecimal PRICE = BigDecimal.valueOf(100);
    private static final String BOOK_NAME = "Road To Java Dev";
    private static final String UPDATED_BOOK_NAME = "Dev Java Road";
    private static final BigDecimal UPDATED_PRICE = BigDecimal.valueOf(500);
    private static final Long DELETED_BOOK_ID = 1L;
    private static final Long UPDATED_BOOK_ID = 1L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(PRICE);
        book.setTitle(BOOK_NAME);
        System.out.println("Created book: " + bookDao.create(book));
        System.out.println("Founded book: " + bookDao.findById(book.getId()));
        System.out.println("All books: " + bookDao.findAll());
        Book newBook = new Book();
        newBook.setTitle(UPDATED_BOOK_NAME);
        newBook.setPrice(UPDATED_PRICE);
        newBook.setId(UPDATED_BOOK_ID);
        System.out.println("Updated book: " + bookDao.update(newBook));
        System.out.println("Deleted book: " + bookDao.deleteById(DELETED_BOOK_ID));
    }
}
