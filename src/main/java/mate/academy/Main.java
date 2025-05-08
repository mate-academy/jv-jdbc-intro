package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book testBook = new Book("Harry Potter", BigDecimal.valueOf(115L));
        Book book = bookDao.create(testBook);
        Optional<Book> bookById = bookDao.findById(1L);
        Book updateTestBook = new Book(10L, "Peter Pan", BigDecimal.valueOf(130L));
        bookDao.update(updateTestBook);
        bookDao.deleteById(6L);
        System.out.println(bookDao.findAll());
    }
}
