package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(23.99));
        book.setTitle("Book");

        System.out.println(bookDao.deleteById(7L));

        System.out.println(bookDao.update(book));

        System.out.println(bookDao.create(book));

        System.out.println(bookDao.findById(1L).get());

        System.out.println(bookDao.findAll());
    }
}
