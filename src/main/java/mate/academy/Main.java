package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book gamlet = new Book();
        gamlet.setTitle("Gamlet");
        gamlet.setPrice(new BigDecimal(100));
        gamlet = bookDao.create(gamlet);
        Book donKihot = new Book();
        donKihot.setTitle("Don Kihot");
        donKihot.setPrice(new BigDecimal(80));
        donKihot = bookDao.create(donKihot);
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(donKihot.getId());
        bookDao.findAll().forEach(System.out::println);
    }
}
