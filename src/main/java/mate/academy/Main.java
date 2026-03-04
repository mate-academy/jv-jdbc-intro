package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setId(8L);
        book.setTitle("I'm legend 3");
        book.setPrice(BigDecimal.valueOf(55.99));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.update(book);
        bookDao.findAll();
    }
}

