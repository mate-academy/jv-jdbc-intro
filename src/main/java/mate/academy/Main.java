package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final BigDecimal PRICE_1 = BigDecimal.valueOf(45.9);
    private static final BigDecimal PRICE_2 = BigDecimal.valueOf(70);
    private static final BigDecimal PRICE_3 = BigDecimal.valueOf(60.9);
    private static final BigDecimal PRICE_4 = BigDecimal.valueOf(65.9);
    private static final BigDecimal NEW_PRICE = BigDecimal.valueOf(80);
    private static final String TITLE_1 = "The Last Wish";
    private static final String TITLE_2 = "The Sword of Destiny";
    private static final String TITLE_3 = "The Season of Storms";
    private static final String TITLE_4 = "Tomer of Swallow";
    private static final String NEW_TITLE = "Lady of the Lake";
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle(TITLE_1);
        book1.setPrice(PRICE_1);

        Book book2 = new Book();
        book2.setTitle(TITLE_2);
        book2.setPrice(PRICE_2);

        Book book3 = new Book();
        book3.setTitle(TITLE_3);
        book3.setPrice(PRICE_3);

        Book book4 = new Book();
        book4.setTitle(TITLE_4);
        book4.setPrice(PRICE_4);

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.create(book4);

        Optional<Book> bookByIdTwo = bookDao.findById(2L);
        bookByIdTwo.ifPresent(System.out::println);

        boolean deleteByIdThree = bookDao.deleteById(3L);
        System.out.println(deleteByIdThree);

        Book bookToUpdate = new Book();
        bookToUpdate.setId(4L);
        bookToUpdate.setTitle(NEW_TITLE);
        bookToUpdate.setPrice(NEW_PRICE);
        bookDao.update(bookToUpdate);

        List<Book> allBooks = bookDao.findAll();
        for (Book book : allBooks) {
            System.out.println(book);
        }
    }
}
