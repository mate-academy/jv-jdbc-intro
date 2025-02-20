package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.lib");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(null, "Java", new BigDecimal("29.99"));
        book = bookDao.create(book);

        Optional<Book> foundBook = bookDao.findById(book.getId());

        List<Book> books = bookDao.findAll();

        book.setTitle("Advanced Java");
        book.setPrice(new BigDecimal("39.99"));
        book = bookDao.update(book);

        boolean isDeleted = bookDao.deleteById(book.getId());

    }
}
