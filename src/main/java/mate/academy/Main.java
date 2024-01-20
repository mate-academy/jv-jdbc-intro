package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(1L,"Harry Potter and The Chamber of Secrets", new BigDecimal(123));
        bookDao.create(book);
        book = new Book(1L,"Lord of The Rings", new BigDecimal(345));
        bookDao.create(book);
        Optional<Book> bookFromDB = bookDao.findById(1L);
        book = new Book(1L, "Harry Potter and The Goblet of Fire", new BigDecimal(321));
        bookDao.update(book);
        bookDao.deleteById(1L);
        List<Book> books = bookDao.findAll();
    }
}
