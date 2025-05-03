package mate.academy;

import dao.BookDao;
import java.math.BigDecimal;
import lib.DataProcessingException;
import lib.Injector;
import model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("dao");

    public static void main(String[] args) throws DataProcessingException {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(200));
        book.setTitle("Java");
        bookDao.create(book);
        bookDao.findById(4L);
        bookDao.findAll();
        bookDao.update(book);
        bookDao.deleteById(4L);
    }
}
