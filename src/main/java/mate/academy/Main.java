package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Create a new book
        Book book = new Book();
        book.setTitle("Effective Java");
        book.setPrice(BigDecimal.valueOf(45.99));
        bookDao.create(book);

        System.out.println("All books:");
        bookDao.findAll().forEach(System.out::println);

        System.out.println("Book found by ID:");
        bookDao.findById(book.getId()).ifPresent(System.out::println);

        book.setTitle("Effective Java 3rd Edition");
        book.setPrice(BigDecimal.valueOf(49.99));
        bookDao.update(book);

        System.out.println("All books after update:");
        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(book.getId());

        System.out.println("All books after deletion:");
        bookDao.findAll().forEach(System.out::println);
    }
}
