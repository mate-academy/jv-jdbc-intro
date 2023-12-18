package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final Long BOOK_TEST_ID = 1L;
    private static final String BOOK_TEST_TITLE = "Star wars";
    private static final BigDecimal BOOK_TEST_PRICE = BigDecimal.valueOf(13.23);
    private static final BigDecimal BOOK_TEST_PRICE_NEW = BigDecimal.valueOf(14.00);

    public static void main(String[] args) {
        Book starWars = new Book();
        starWars.setId(BOOK_TEST_ID);
        starWars.setTitle(BOOK_TEST_TITLE);
        starWars.setPrice(BOOK_TEST_PRICE);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(starWars);
        Optional<Book> bookFounded = bookDao.findById(starWars.getId());
        System.out.println("Added: " + (bookFounded.isPresent() ? bookFounded.get() : "No"));
        starWars.setPrice(BOOK_TEST_PRICE_NEW);
        bookDao.update(starWars);
        bookFounded = bookDao.findById(starWars.getId());
        System.out.println("Updated: " + (bookFounded.isPresent() ? bookFounded.get() : "No"));
        System.out.println("Deleted: " + starWars + " " + bookDao.deleteById(starWars.getId()));
        List<Book> listBooks = bookDao.findAll();
        if (listBooks.size() > 0) {
            System.out.println("Now have in table books:");
            for (Book oneBook : listBooks) {
                System.out.println(oneBook);
            }
        }
    }
}
