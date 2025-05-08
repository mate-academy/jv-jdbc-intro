package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.exceptions.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Alice in Wonderland");
        book.setPrice(BigDecimal.valueOf(160));
        book = bookDao.create(book);
        book = bookDao.findById(book.getId()).orElseThrow(() -> new DataProcessingException(
                "Can't get the book by ID.", new SQLException()));
        List<Book> books = bookDao.findAll();
        books = bookDao.findAll();
        book.setTitle("Alice Through the Looking Glass");
        book.setPrice(BigDecimal.valueOf(115));
        book = bookDao.update(book);
        boolean deleted = bookDao.deleteById(book.getId());
    }
}
