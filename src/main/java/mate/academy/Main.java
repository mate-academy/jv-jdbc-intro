package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.lib");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setId(null);
        book.setTitle("Java+");
        book.setPrice(new BigDecimal("50.0"));
        book = bookDao.create(book);

        Optional<Book> findBook = bookDao.findById(book.getId());

        List<Book> books = bookDao.findAll();

        book.setTitle("Java++");
        book.setPrice(new BigDecimal("70.0"));

        book = bookDao.update(book);

        boolean isDel = bookDao.deleteById(book.getId());
    }
}
