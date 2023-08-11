package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book threeComradesBook = new Book();
        threeComradesBook.setTitle("Three Comrades");
        threeComradesBook.setPrice(BigDecimal.valueOf(100));
        Book theThreePigs = new Book();
        theThreePigs.setTitle("The Three Pigs");
        theThreePigs.setPrice(BigDecimal.valueOf(90));
        Book theThreeBrothers = new Book();
        theThreeBrothers.setTitle("The three brothers");
        theThreeBrothers.setPrice(BigDecimal.valueOf(50));

        bookDao.create(threeComradesBook);
        bookDao.create(theThreePigs);
        bookDao.create(theThreeBrothers);
        threeComradesBook.setPrice(BigDecimal.valueOf(130));
        bookDao.update(threeComradesBook);
        System.out.println(bookDao.findById(1L));
        bookDao.deleteById(3L);
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);
    }
}
