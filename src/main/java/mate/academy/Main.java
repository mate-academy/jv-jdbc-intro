package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book lisovaPisnyaBook = new Book();
        Book kobzarBook = new Book();
        lisovaPisnyaBook.setTitle("Lisova Pisnya");
        kobzarBook.setTitle("Kobzar");
        lisovaPisnyaBook.setPrice(BigDecimal.valueOf(100));
        kobzarBook.setPrice(BigDecimal.valueOf(150));
        lisovaPisnyaBook = bookDao.create(lisovaPisnyaBook);
        kobzarBook = bookDao.create(kobzarBook);

        System.out.println(bookDao.findById(kobzarBook.getId()));

        bookDao.findAll().forEach(System.out::println);

        lisovaPisnyaBook.setPrice(BigDecimal.valueOf(80));
        bookDao.update(lisovaPisnyaBook);

        System.out.println(bookDao.deleteById(kobzarBook.getId()));
        System.out.println(bookDao.deleteById(50L));
    }
}
