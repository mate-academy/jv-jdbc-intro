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
                new Book(1L, "Book1", BigDecimal.valueOf(5.99)),
                new Book(2L, "Book2", BigDecimal.valueOf(4.35)),
                new Book(3L, "Book3", BigDecimal.valueOf(10.00))
        );
        books.forEach(bookDao::create);
        Book secondBook = books.get(1);
        secondBook.setTitle("Book4");
        bookDao.update(secondBook);
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());
        bookDao.deleteById(3L);

        // initialize field values using setters or constructor
        //bookDao.create(book);
        // test other methods from BookDao

    }
}
