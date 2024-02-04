package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(6L,"Think JAVA", new BigDecimal(2300));
        bookDao.create(book);
        bookDao.findById(6L);
        bookDao.findAll();
        book.setPrice(new BigDecimal(2000));
        bookDao.deleteById(8L);
        bookDao.update(book);

    }
}
