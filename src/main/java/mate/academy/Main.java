package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.create(new Book("book1", new BigDecimal(123)));
        bookDao.create(new Book("book2", new BigDecimal(456)));
        bookDao.create(new Book("book3", new BigDecimal(789)));
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
        bookDao.update(new Book(1L, "newBook", new BigDecimal(1000)));
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
        bookDao.deleteById(1L);
        bookDao.findAll().forEach(System.out::println);
        System.out.println();
        System.out.println(bookDao.findById(2L).toString());
        bookDao.deleteById(2L);
        bookDao.deleteById(3L);
        bookDao.findAll().forEach(System.out::println);
    }
}
