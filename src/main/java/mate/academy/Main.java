package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("I'm legend 2");
        book.setPrice(BigDecimal.valueOf(55.99));
        bookDao.create(book);

        Book book1 = new Book();
        book.setTitle("Spirit retern");
        book.setPrice(BigDecimal.valueOf(15));
        bookDao.update(book1);
        bookDao.findAll();
        bookDao.findById(1L);
        bookDao.deleteById(4L);
    }
}

