package mate.academy;

import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.lib.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Effective Java", new BigDecimal("39.99"));
        bookDao.create(book);

        bookDao.findAll().forEach(System.out::println);
    }
}