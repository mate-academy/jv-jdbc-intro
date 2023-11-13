package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Optional<Book> byId = bookDao.findById(1L);
        Book testBook1 = new Book("Test Book", BigDecimal.valueOf(111));
        Book createdBook = bookDao.create(testBook1);
        bookDao.deleteById(3L);
        List<Book> list = bookDao.findAll();
    }
}
