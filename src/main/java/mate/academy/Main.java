package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Harry Potter", new BigDecimal("100.00"));
        Book book2 = new Book("Lord of the Rings", new BigDecimal("200.00"));
        bookDao.create(book);
        bookDao.create(book2);

        System.out.println("\n Book with id: 1");
        System.out.println(bookDao.findById(1L));

        System.out.println("\n Book with id: 2");
        System.out.println(bookDao.findById(2L));

        System.out.println("\n All books:");
        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        Book book3 = new Book(1L,"Parry Hotter", new BigDecimal("001.01"));
        bookDao.update(book3);
        System.out.println("\n Updated book with id: 1");
        System.out.println(bookDao.findById(1L));

        bookDao.deleteById(1L);
        System.out.println("\n Deleted book with id: 1");
        books = bookDao.findAll();
        books.forEach(System.out::println);
    }
}
