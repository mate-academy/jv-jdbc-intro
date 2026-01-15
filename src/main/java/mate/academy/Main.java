package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Animal Farm");
        book.setPrice(BigDecimal.valueOf(145));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.findById(1L);
        bookDao.findAll();

        book.setPrice(BigDecimal.valueOf(120));
        bookDao.update(book);
        bookDao.deleteById(2L);
    }
}
