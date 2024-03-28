package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.findAll());
        Book kobzar = new Book("Kobzar", new BigDecimal("54.99"));
        kobzar = bookDao.create(kobzar);
        System.out.println(kobzar);
        System.out.println(bookDao.findAll());
        kobzar.setPrice(new BigDecimal("25.99"));
        bookDao.update(kobzar);
        System.out.println(bookDao.findAll());
        bookDao.deleteById(kobzar.getId());
        System.out.println(bookDao.findAll());
    }
}
