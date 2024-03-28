package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book mainCamp = new Book();
        mainCamp.setTitle("Main Camp");
        mainCamp.setPrice(BigDecimal.valueOf(19.99));
        bookDao.create(mainCamp);

        Book lordOfTheRing = new Book();
        lordOfTheRing.setTitle("Lord of the ring");
        lordOfTheRing.setPrice(BigDecimal.valueOf(20.50));
        bookDao.create(lordOfTheRing);

        bookDao.findAll();
        bookDao.findById(1L);
        bookDao.update(lordOfTheRing).setPrice(BigDecimal.valueOf(40));
        bookDao.deleteById(1L);
    }
}
