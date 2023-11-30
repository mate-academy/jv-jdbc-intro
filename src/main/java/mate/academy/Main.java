package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Thinking in Java");
        book.setPrice(BigDecimal.valueOf(500));

        Book book1 = new Book();
        book1.setTitle("Effective Java");
        book1.setPrice(BigDecimal.valueOf(450));

        Book book2 = new Book();
        book2.setTitle("Modern Java");
        book2.setPrice(BigDecimal.valueOf(350));

        bookDao.create(book);
        bookDao.create(book1);
        bookDao.create(book2);
        System.out.println(bookDao.findAll());
        /*
        bookDao.deleteById(13L);
        bookDao.deleteById(9L);
        bookDao.deleteById(10L);
        bookDao.deleteById(11L);
        */
        book.setPrice(BigDecimal.valueOf(1000));
        bookDao.update(book);

        System.out.println(bookDao.findById(3L));
        System.out.println(bookDao.deleteById(3L));
    }
}
