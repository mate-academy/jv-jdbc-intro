package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.Dao;
import mate.academy.dao.impl.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("Animal Farm");
        book1.setPrice(new BigDecimal("7.99"));

        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setPrice(new BigDecimal("10.93"));

        BookDao bookDao = (BookDao) injector.getInstance(Dao.class);

        System.out.println("Adding 2 books in db\n");
        bookDao.create(book1);
        bookDao.create(book2);

        System.out.println("Reading all data from db");
        System.out.println(bookDao.findAll() + "\n");

        System.out.println("Changing first book\n");
        book1.setPrice(new BigDecimal("8.50"));
        bookDao.update(book1);

        System.out.println("Reading data about 1st book from db");
        System.out.println(bookDao.findById(1L).get() + "\n");

        System.out.println("Dropping 2nd book from db\n");
        bookDao.deleteById(2L);

        System.out.println("Reading all data from db");
        System.out.println(bookDao.findAll());
    }
}
