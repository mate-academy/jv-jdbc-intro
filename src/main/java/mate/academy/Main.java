package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(100));
        System.out.println(bookDao.create(book));
        Book book2 = new Book();
        book2.setTitle("Effective Java");
        book2.setPrice(BigDecimal.valueOf(200));
        System.out.println(bookDao.create(book2));
        System.out.println(bookDao.update(book2));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.deleteById(1L));
    }
}
