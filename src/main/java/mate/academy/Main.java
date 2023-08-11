package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final String FIRST_BOOK_NAME = "SQL: Revenge of the Query";
    private static final String SECOND_BOOK_NAME = "SQL: Statements Attack!";
    private static final String UPDATE_BOOK_NAME = "SQL: Faster Then Light";
    private static final BigDecimal FIRST_TEST_BOOK_PRICE = BigDecimal.valueOf(500);
    private static final BigDecimal SECOND_TEST_BOOK_PRICE = BigDecimal.valueOf(1200);
    private static final BigDecimal THIRD_TEST_BOOK_PRICE = BigDecimal.valueOf(700);
    private static final Long TEST_BOOK_ID = 13L;
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book(FIRST_BOOK_NAME, FIRST_TEST_BOOK_PRICE);
        bookDao.create(firstBook);
        Book secondBook = new Book(SECOND_BOOK_NAME, SECOND_TEST_BOOK_PRICE);
        bookDao.create(secondBook);
        bookDao.deleteById(firstBook.getId());
        System.out.println(bookDao.findById(TEST_BOOK_ID).orElseThrow(
                () -> new RuntimeException("Can`t get book by id" + TEST_BOOK_ID)));
        Book bookForUpdate = new Book(TEST_BOOK_ID, UPDATE_BOOK_NAME,
                THIRD_TEST_BOOK_PRICE);
        bookDao.update(bookForUpdate);
        System.out.println(bookDao.findAll());
    }
}
