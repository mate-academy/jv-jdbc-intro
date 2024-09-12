package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(207));
        book.setTitle("Sherlock Holmes");
        book.setId(4L);
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.findById(4L);
        bookDao.deleteById(3L);
        bookDao.update(book);
        bookDao.findAll();
    }
}
