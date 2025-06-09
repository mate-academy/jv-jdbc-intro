package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Long TEST_ID = 2L;
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(new Book("Harry Potter", new BigDecimal(1000)));
        Book sherlockHolmes = bookDao.create(new Book("Sherlock Holmes", new BigDecimal(300)));
        bookDao.create(new Book("Black swan", new BigDecimal(500)));
        bookDao.create(new Book("Yellow king", new BigDecimal(700)));
        Optional<Book> checkGetBookById = bookDao.findById(TEST_ID);
        System.out.println(checkGetBookById);
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);
        bookDao.findById(1L);
        bookDao.update(new Book(3L, "Peter Pan", new BigDecimal(400)));
        bookDao.deleteById(TEST_ID);
    }
}
