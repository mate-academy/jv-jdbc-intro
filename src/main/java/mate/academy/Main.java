package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Java", BigDecimal.valueOf(870));
        Book book2 = new Book("All about fishing", BigDecimal.valueOf(350));

        System.out.println("Created books:");
        Book bookWithId = bookDao.create(book);
        System.out.println(bookWithId);
        Book book2WithId = bookDao.create(book2);
        System.out.println(book2WithId);

        System.out.println("Find methods testing:");
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findById(5L));

        System.out.println("Update books:");
        bookWithId.setTitle("Hibernate");
        bookWithId.setPrice(BigDecimal.valueOf(550));
        System.out.println("Old book: " + bookDao.update(bookWithId));
        System.out.println("Updated book: " + bookDao.update(bookWithId));
        Book bookWithNotExistingId = new Book("Some book", BigDecimal.valueOf(300));
        bookWithNotExistingId.setId(10L);
        System.out.println(bookDao.update(bookWithNotExistingId));

        System.out.println("Delete book");
        System.out.println("Delete with id 2: " + bookDao.deleteById(2L));
        System.out.println("Delete with id 20: " + bookDao.deleteById(20L));
    }
}
