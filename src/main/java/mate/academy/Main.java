package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setPrice(new BigDecimal("45.99"));
        bookDao.create(book);
        Optional<Book> fromDb = bookDao.findById(book.getId());
        fromDb.ifPresent(System.out::println);
        List<Book> allBooks = bookDao.findAll();
        allBooks.forEach(System.out::println);
        book.setPrice(new BigDecimal("49.99"));
        bookDao.update(book);
        bookDao.deleteById(book.getId());
    }
}

