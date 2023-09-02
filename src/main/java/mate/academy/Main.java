package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookDao;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book = new Book(1L,"title",new BigDecimal(21));
        Book bookForUpdate = new Book(1L,"testTitle", new BigDecimal(12));
        bookDao.findAll();
        bookDao.create(book);
        bookDao.deleteById(1L);
        bookDao.update(bookForUpdate);
    }
}
