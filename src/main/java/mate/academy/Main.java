package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final String BOOK_TEST_TITLE = "Star wars";
    private static final BigDecimal BOOK_TEST_PRICE = BigDecimal.valueOf(13.23);
    private static final BigDecimal BOOK_TEST_PRICE_NEW = BigDecimal.valueOf(14.00);

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(BOOK_TEST_TITLE, BOOK_TEST_PRICE);
        bookDao.create(book);
        System.out.println("Was added : " + bookDao.findById(book.getId()).get());
        book.setPrice(BOOK_TEST_PRICE_NEW);
        bookDao.update(book);
        System.out.println("Was updated : " + bookDao.findById(book.getId()).get());
        System.out.println("Was deleted : " + book + " " + bookDao.deleteById(book.getId()));
        List<Book> listBooks = bookDao.findAll();
        if (listBooks.size() > 0) {
            System.out.println("Now have in table books:");
            for (Book oneBook : listBooks) {
                System.out.println(oneBook);
            }
        }
    }
}
