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
        book.setTitle("Thinking in Java");
        book.setPrice(BigDecimal.valueOf(55.09));
        bookDao.create(book);
        bookDao.findById(8L);
        bookDao.findAll();
        book.setPrice(BigDecimal.valueOf(50.00));
        bookDao.update(book);
    }
}
