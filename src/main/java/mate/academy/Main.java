package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String BOOK_NAME_1 = "Clean code";
    private static final String BOOK_NAME_2 = "Spring in Action";
    private static final String BOOK_NAME_3 = "Test-Driven Java Development";
    private static final BigDecimal BOOK_PRICE_1 = BigDecimal.valueOf(25.33);
    private static final BigDecimal BOOK_PRICE_2 = BigDecimal.valueOf(39.51);
    private static final BigDecimal BOOK_PRICE_3 = BigDecimal.valueOf(49.99);
    private static final BigDecimal BOOK_PRICE_NEW = BigDecimal.valueOf(27.20);
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book(BOOK_NAME_1, BOOK_PRICE_1);
        Book book2 = new Book(BOOK_NAME_2, BOOK_PRICE_2);
        Book book3 = new Book(BOOK_NAME_3, BOOK_PRICE_3);
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.findById(1L);
        List<Book> listBooks = bookDao.findAll();
        for (Book book: listBooks) {
            if (book.getTitle().equals(BOOK_NAME_1)) {
                book.setPrice(BOOK_PRICE_NEW);
                bookDao.update(book);
            }
        }
        for (Book book: listBooks) {
            bookDao.deleteById(book.getId());
        }
    }
}
