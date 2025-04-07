package mate.academy;

import dao.BookDao;
import java.math.BigDecimal;
import lib.Injector;
import model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        BigDecimal.valueOf(200);
        book.setTitle("Java");
        bookDao.create(book);
        bookDao.findById(4L);
        bookDao.findAll();
        bookDao.update(book);
        bookDao.deleteById(4L);
    }
}
