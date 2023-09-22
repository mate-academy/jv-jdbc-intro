package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("book");
        book.setPrice(BigDecimal.valueOf(100));

        bookDao.create(book);

        // Optional<Book> byId = bookDao.findById(1L);

        Book book1 = new Book();
        book1.setTitle("boook");
        book1.setPrice(BigDecimal.valueOf(13));
        book1.setId(1L);
        Book update = bookDao.update(book1);
        System.out.println(update);

        List<Book> all = bookDao.findAll();
        System.out.println(all);

        boolean b = bookDao.deleteById(1L);
        System.out.println(b);

        // bookDao.findById(1L);
    }
}
