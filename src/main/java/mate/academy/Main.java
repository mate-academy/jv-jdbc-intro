package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = new BookDao();
        bookDao.create(new Book("a", BigDecimal.ONE));
        bookDao.create(new Book("b", BigDecimal.ONE));
        bookDao.update(new Book(14L, "c", BigDecimal.ONE));
        bookDao.deleteById(15L);
        System.out.println(bookDao.findById(16L));
        System.out.println(bookDao.findAll().toString());
    }
}
