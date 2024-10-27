package mate.academy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("src.main.java.mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = Arrays.asList(
                new Book(1L, "Catch-22", BigDecimal.valueOf(5.37)),
                new Book(2L, "Winnie the Pooh", BigDecimal.valueOf(254.01)),
                new Book(3L, "Harry Potter", BigDecimal.valueOf(90.00)),
                new Book(4L, "Crime and Punishment", BigDecimal.valueOf(15.00))
        );

        books.forEach(bookDao::create);
        Book secondBook = books.get(1);
        secondBook.setTitle("No name");
        bookDao.update(secondBook);
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());
        bookDao.deleteById(3L);

    }
}
