package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String MAIN_PACKAGE_NAME = "mate.academy.dao";
    private static final String FIRST_BOOK_TITLE = "Alice in the Wonderland";
    private static final String SECOND_BOOK_TITLE = "Lord of the Rings";
    private static final String THIRD_BOOK_TITLE = "Atomic habits";
    private static final BigDecimal FIRST_BOOK_PRICE = BigDecimal.valueOf(300);
    private static final BigDecimal SECOND_BOOK_PRICE = BigDecimal.valueOf(1200);
    private static final BigDecimal THIRD_BOOK_PRICE = BigDecimal.valueOf(650);

    public static void main(String[] args) {
        Injector injector = Injector.getInstance(MAIN_PACKAGE_NAME);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle(FIRST_BOOK_TITLE);
        book.setPrice(FIRST_BOOK_PRICE);
        book = bookDao.create(book);
        book.setTitle(SECOND_BOOK_TITLE);
        book.setPrice(SECOND_BOOK_PRICE);
        book = bookDao.create(book);
        book.setTitle(THIRD_BOOK_TITLE);
        book.setPrice(THIRD_BOOK_PRICE);
        bookDao.update(book);
        Book foundBook = bookDao.findById(1L).get();
        bookDao.deleteById(book.getId());
        System.out.println(bookDao.findAll());
    }
}
