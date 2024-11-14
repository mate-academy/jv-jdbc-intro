package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookOne = new Book("book one", BigDecimal.valueOf(1022));
        Book bookTwo = new Book(4L,"book two", BigDecimal.valueOf(12));
        bookDao.create(bookOne);
        bookDao.update(bookTwo);
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        bookDao.deleteById(1L);
    }
}
