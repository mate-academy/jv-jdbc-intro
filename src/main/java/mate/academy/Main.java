package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book = new Book("Book", BigDecimal.valueOf(100));
        // initialize field values using setters or constructor
        bookDao.create(book);
        System.out.println(bookDao.findById(1L));
        bookDao.findAll().forEach(System.out::println);
        // test other methods from BookDao

    }
}
