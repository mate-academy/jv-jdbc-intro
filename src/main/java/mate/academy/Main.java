package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("TestBook2");
        book.setPrice(BigDecimal.valueOf(3.3));
        bookDao.create(book);
        Optional<Book> byId = bookDao.findById(2L);
        bookDao.deleteById(1L);
        bookDao.update(book);
        bookDao.findAll();
    }
}
