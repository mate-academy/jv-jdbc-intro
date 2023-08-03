package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("firstBook");
        firstBook.setPrice(BigDecimal.valueOf(10));
        Book secondBook = new Book();
        secondBook.setTitle("secondBook");
        secondBook.setPrice(BigDecimal.valueOf(20));
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        System.out.println(bookDao.findById(firstBook.getId()));
        bookDao.findAll().forEach(System.out::println);
        firstBook.setPrice(BigDecimal.valueOf(30));
        firstBook.setTitle("newFirstBook");
        bookDao.update(firstBook);
        System.out.println(bookDao.deleteById(secondBook.getId()));
    }
}
