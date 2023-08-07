package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("interesting book");
        firstBook.setPrice(BigDecimal.valueOf(32));

        Book secondBook = new Book();
        secondBook.setTitle("funny book");
        secondBook.setPrice(BigDecimal.valueOf(50));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("First book was created: " + bookDao.create(firstBook));
        System.out.println("Second book was created: " + bookDao.create(secondBook));
        System.out.println("Get first book by findById method: " + bookDao.findById(1L).get());

        secondBook.setTitle("sad book");
        secondBook.setPrice(BigDecimal.valueOf(28));
        System.out.println("Second book was updated: " + bookDao.update(secondBook));
        System.out.println("All books before deleting: " + bookDao.findAll());
        System.out.println("Second book was deleted: " + bookDao.deleteById(2L));
        System.out.println("All books after deleting: " + bookDao.findAll());

    }
}
