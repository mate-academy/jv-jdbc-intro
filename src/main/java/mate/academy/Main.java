package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book("UKRAINE", BigDecimal.valueOf(100.50));
        Book secondBook = new Book("WAR", BigDecimal.valueOf(70.50));
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.findAll();
        bookDao.findById(firstBook.getId());
        firstBook.setTitle("PEREMOHA");
        bookDao.update(firstBook);
        bookDao.deleteById(secondBook.getId());
    }
}
