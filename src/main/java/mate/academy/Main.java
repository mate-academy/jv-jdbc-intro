package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");
    public static void main(String[] args) {
        Book gamlet = new Book();
        gamlet.setTitle("Gamlet");
        gamlet.setPrice(BigDecimal.valueOf(1500));

        Book korolLitr = new Book();
        korolLitr.setTitle("Korol' litr");
        korolLitr.setPrice(BigDecimal.valueOf(1250));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(gamlet);
        bookDao.create(korolLitr);

        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        gamlet.setPrice(BigDecimal.valueOf(999));
        System.out.println(bookDao.update(gamlet));
        bookDao.deleteById(1L);
        System.out.println(bookDao.findById(1L));
    }
}
