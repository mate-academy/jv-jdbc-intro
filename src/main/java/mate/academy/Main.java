package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Properties;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book("The Chronical Of Amber", new BigDecimal("10.99"));
        bookDao.create(firstBook);

        Book secondBook = new Book("Shogun",new BigDecimal("9.02"));
        bookDao.create(secondBook);

        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(1L).get());

        Book book = bookDao.findById(1L).get();
        book.setTitle("Updated Title");
        bookDao.update(book);
        System.out.println(bookDao.findById(1L).get());

        System.out.println(bookDao.deleteById(1L));
        System.out.println(bookDao.findAll());
    }
}
