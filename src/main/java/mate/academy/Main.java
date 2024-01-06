package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) throws Exception {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bobsBook = new Book();
        bobsBook.setTitle("Head First Java");
        bobsBook.setPrice(new BigDecimal(350));
        bookDao.create(bobsBook);
        bookDao.findById(1L);
        bookDao.findAll();
        Book alicesBook = new Book();
        alicesBook.setTitle("Story of salvation");
        alicesBook.setPrice(new BigDecimal(256));
        alicesBook.setId(1L);
        bookDao.update(alicesBook);
        bookDao.deleteById(6L);
    }
}
