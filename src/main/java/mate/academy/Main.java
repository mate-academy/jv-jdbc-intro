package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setId(7L);
        book.setTitle("Wild heart");
        book.setPrice(new BigDecimal(500));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.findById(3L);
        bookDao.findAll();
        bookDao.update(book);
        bookDao.deleteById(book.getId());
    }
}
