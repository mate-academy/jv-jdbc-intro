package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");
        book.setPrice(BigDecimal.valueOf(100));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(book);
        book.setTitle("Book 2");
        bookDao.update(book);
        List<Book> books = bookDao.findAll();
        bookDao.findById(book.getId());
        bookDao.deleteById(book.getId());
        // test other methods from BookDao
    }
}
