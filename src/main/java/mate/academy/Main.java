package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import model.Book;

public class Main {
    private static final String TITLE_BOOK_ONE = "Last battle";
    private static final String TITLE_BOOK_TWO = "Green leaves";
    private static final String NEW_TITLE_BOOK = "Green leave.Part 2";
    private static final String TITLE_BOOK_THREE = "What Is a Legal Trust?";
    private static final BigDecimal PRICE_BOOK_ONE = BigDecimal.valueOf(35.99);
    private static final BigDecimal PRICE_BOOK_TWO = BigDecimal.valueOf(21.99);
    private static final BigDecimal NEW_PRICE_BOOK = BigDecimal.valueOf(22.99);
    private static final BigDecimal PRICE_BOOK_THREE = BigDecimal.valueOf(19.99);
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book bookOne = new Book();
        bookOne.setTitle(TITLE_BOOK_ONE);
        bookOne.setPrice(PRICE_BOOK_ONE);

        Book bookTwo = new Book();
        bookTwo.setTitle(TITLE_BOOK_TWO);
        bookTwo.setPrice(PRICE_BOOK_TWO);

        Book bookTree = new Book();
        bookTree.setTitle(TITLE_BOOK_THREE);
        bookTree.setPrice(PRICE_BOOK_THREE);

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        bookDao.create(bookTree);

        Optional<Book> bookIdThree = bookDao.findById(3L);
        System.out.println(bookIdThree.isPresent());

        List<Book> listBooks = bookDao.findAll();
        for (Book book : listBooks) {
            System.out.println(book);
        }

        Book updateBook = new Book();
        updateBook.setId(3L);
        updateBook.setTitle(NEW_TITLE_BOOK);
        updateBook.setPrice(NEW_PRICE_BOOK);
        bookDao.update(updateBook);

        boolean deleteBookByIdOne = bookDao.deleteById(1L);
        System.out.println(deleteBookByIdOne);
    }
}
