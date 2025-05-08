package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book();
        book1.setTitle("Java");
        book1.setPrice(BigDecimal.valueOf(435.44));
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("CleanCode");
        book2.setPrice(BigDecimal.valueOf(122.44));
        bookDao.create(book2);

        book1.setPrice(BigDecimal.valueOf(120));
        bookDao.update(book1);

        System.out.println(bookDao.findById(16L));
        System.out.println(bookDao.findAll());

        bookDao.deleteById(21L);
        bookDao.deleteById(22L);
    }
}
