package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Fantastic Beasts and where to find them ", BigDecimal.valueOf(29.5));
        Book bookToUpd = new Book(1L, "Harry Potter: Magic Stone", BigDecimal.valueOf(39.99));
        // initialize field values using setters or constructor
        bookDao.create(book);
        bookDao.update(bookToUpd);
        System.out.println(bookDao.findById(2L));
        bookDao.findAll().forEach(System.out::println);
        bookDao.deleteById(7L);
        // test other methods from BookDao
    }
}
