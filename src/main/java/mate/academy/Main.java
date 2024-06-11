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
        Book book = new Book();
        book.setTitle("Java");
        book.setPrice(BigDecimal.valueOf(100));
        bookDao.create(book);
        Optional<Book> optionalBook1 = bookDao.findById(book.getId());
        List<Book> allBooks = bookDao.findAll();
        book.setId(25L);
        book.setTitle("Updated Book");
        book.setPrice(BigDecimal.valueOf(5000));
        bookDao.update(book);
        bookDao.deleteById(25L);
    }
}
