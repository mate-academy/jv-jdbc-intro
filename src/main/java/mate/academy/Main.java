package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book alien = new Book();
        alien.setTitle("Alien");
        alien.setPrice(BigDecimal.valueOf(800));
        System.out.println(bookDao.create(alien));

        Book shekspir = new Book();
        shekspir.setTitle("Shekspir");
        shekspir.setPrice(BigDecimal.valueOf(1200));
        System.out.println(bookDao.create(shekspir));

        bookDao.findById(1L).ifPresent(System.out::println);

        System.out.println(bookDao.findAll());

        Book updated = new Book();
        updated.setId(2L);
        updated.setTitle("Shekspir");
        updated.setPrice(BigDecimal.valueOf(1500));
        System.out.println(bookDao.update(updated));

        System.out.println(bookDao.deleteById(1L));
    }
}
