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
        book.setPrice(new BigDecimal(111));
        book.setTitle("Best Book");
        book.setId(1L);

        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(1L));
    }
}
