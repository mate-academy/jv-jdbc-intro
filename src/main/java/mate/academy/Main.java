package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book theTimeBook = new Book();
        theTimeBook.setId(1L);
        theTimeBook.setTitle("The time");
        theTimeBook.setPrice(BigDecimal.valueOf(80));
        Book myWarBook = new Book(3L, "My war", BigDecimal.valueOf(130));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.create(myWarBook));
        System.out.println(bookDao.findById(1L).get());
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.update(theTimeBook));
        System.out.println(bookDao.deleteById(2L));
    }
}
