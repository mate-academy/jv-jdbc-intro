package mate.academy;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> books = Arrays.asList(
                new Book(1L, "Clean Code", BigDecimal.valueOf(13.22)),
                new Book(2L, "Patterns", BigDecimal.valueOf(15.00)),
                new Book(3L, "Reflection API", BigDecimal.valueOf(12.99))
        );
        books.forEach(bookDao::create);
        Book secondBook = books.get(1);
        secondBook.setTitle("Java");
        bookDao.update(secondBook);
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());
        bookDao.deleteById(3L);
    }
}
