package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book lordOfRing = new Book(1L,"Lord of Rings", BigDecimal.valueOf(500));
        Book theWitcher = new Book(2L, "The Witcher", BigDecimal.valueOf(600));
        bookDao.create(lordOfRing);
        bookDao.create(theWitcher);
        bookDao.findById(2L);
        bookDao.findAll();
        Book theWitherUpdate = new Book(2L, "The Witcher Update", BigDecimal.valueOf(10000));
        bookDao.update(theWitherUpdate);
        bookDao.deleteById(1L);
    }
}
