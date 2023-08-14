package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        List<Book> all = bookDao.findAll();
        all.forEach(System.out::println);
        bookDao.deleteById(6L);
        all = bookDao.findAll();
        all.forEach(System.out::println);
    }
}
