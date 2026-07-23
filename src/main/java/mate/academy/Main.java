package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("First Book");
        firstBook.setPrice(BigDecimal.valueOf(50.90));

        Book secondBook = new Book();
        secondBook.setTitle("Second Book");
        secondBook.setPrice(BigDecimal.valueOf(28.80));

        Book thirdBook = new Book();
        thirdBook.setTitle("Third Book");
        thirdBook.setPrice(BigDecimal.valueOf(34.99));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        System.out.println(bookDao.findAll());

        System.out.println(bookDao.deleteById(25L));
        System.out.println(bookDao.findById(26L));

        secondBook = new Book();
        secondBook.setTitle("updated Book");
        secondBook.setPrice(BigDecimal.valueOf(999.999));
        secondBook.setId(26L);

        System.out.println(bookDao.update(secondBook));
    }
}
