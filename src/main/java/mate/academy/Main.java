package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book bookFirst = new Book();
        bookFirst.setId(1L);
        bookFirst.setTitle("Book 1");
        bookFirst.setPrice(BigDecimal.valueOf(30.20));

        Book bookCreateMethod = bookDao.create(bookFirst);

        Optional<Book> findById = bookDao.findById(1L);
        List<Book> findAll = bookDao.findAll();


        Book bookSecond = new Book();
        bookSecond.setId(2L);
        bookSecond.setTitle("Book 2");
        bookSecond.setPrice(BigDecimal.valueOf(50.00));

        Book bookUpdateMethod = bookDao.update(bookSecond);

        bookDao.deleteById(bookUpdateMethod.getId());
    }
}
