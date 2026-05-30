package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book(1L, "Java Basic", BigDecimal.valueOf(99));
        Book newBook = new Book(2L, "Java Advanced", BigDecimal.valueOf(99));
        Book toChange = new Book(2L, "Java ", BigDecimal.valueOf(95));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.create(newBook);
        System.out.println(bookDao.update(toChange));
        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.deleteById(18L));
    }
}
