package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.model.BookDao;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Potop");
        book.setPrice(BigDecimal.valueOf(27.50));
        bookDao.create(book);
        book.setPrice(BigDecimal.valueOf(36.99));
        bookDao.update(book);
        bookDao.deleteById(book.getId());

        bookDao.create(book);
        book.setPrice(BigDecimal.valueOf(27.50));
        bookDao.create(book);
        book.setTitle("Krzyżacy");
        book.setPrice(BigDecimal.valueOf(23.99));
        bookDao.create(book);

        System.out.println(bookDao.findById(3L));
        System.out.println(bookDao.findAll());
    }
}
