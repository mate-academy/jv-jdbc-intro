package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookJava = new Book();
        bookJava.setTitle("Java");
        bookJava.setPrice(BigDecimal.valueOf(350.00));
        bookDao.create(bookJava);
        Book bookHistory = new Book();
        bookHistory.setTitle("History");
        bookHistory.setPrice(BigDecimal.valueOf(350.00));
        bookDao.create(bookHistory);
        Optional<Book> optional = bookDao.findById(3L);
        Book book = optional.get();
        System.out.println(book);
        List<Book> list = bookDao.findAll();
        System.out.println(list);
        bookDao.deleteById(1L);
    }
}
