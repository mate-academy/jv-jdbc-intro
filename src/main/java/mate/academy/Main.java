package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Test8", BigDecimal.valueOf(1022));
        Book book2 = new Book(4L,"Test9", BigDecimal.valueOf(12));
        bookDao.create(book1);
        bookDao.update(book2);
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        bookDao.deleteById(1L);
    }
}
