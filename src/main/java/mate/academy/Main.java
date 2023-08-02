package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book it = new Book();
        it.setTitle("IT");
        it.setPrice(BigDecimal.TEN);

        Book itCreated = bookDao.create(it);
        System.out.println(bookDao.findById(itCreated.getId()));

        Book java = new Book();
        java.setTitle("Java");
        java.setPrice(BigDecimal.ONE);
        Book javaCreated = bookDao.create(java);
        System.out.println(bookDao.findById(javaCreated.getId()));

        java.setTitle("Python");
        bookDao.update(java);
        bookDao.deleteById(itCreated.getId());
        System.out.println(bookDao.findAll());
    }
}
