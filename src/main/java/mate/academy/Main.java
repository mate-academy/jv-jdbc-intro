package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Shining");
        book.setPrice(new BigDecimal("1.11"));

        bookDao.create(book);

        Optional<Book> bookById = bookDao.findById(1L);
        List<Book> books = bookDao.findAll();

        book.setPrice(new BigDecimal("2.22"));
        bookDao.update(book);

        bookDao.deleteById(1L);
    }
}
