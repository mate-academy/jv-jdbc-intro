package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Long id = 1L;
        BigDecimal price = BigDecimal.valueOf(2.99);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(id, "Title", price);
        bookDao.create(book);
        bookDao.findById(book.getId());
        bookDao.update(book);
    }
}
