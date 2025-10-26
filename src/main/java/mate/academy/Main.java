package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // CREATE
        Book book = new Book("Clean Code", new BigDecimal("49.99"));
        bookDao.create(book);
        System.out.println("Book created: " + book);

        // READ - findById
        Long bookId = book.getId();
        Book foundBook = bookDao.findById(bookId).orElse(null);
        System.out.println("Book found by id: " + foundBook);

        // READ - findAll
        System.out.println("All books:");
        bookDao.findAll().forEach(System.out::println);

        // UPDATE
        book.setPrice(new BigDecimal("59.99"));
        Book updatedBook = bookDao.update(book);
        System.out.println("Book after update: " + updatedBook);

        // DELETE
        boolean deleted = bookDao.deleteById(bookId);
        System.out.println("Book deleted: " + deleted);

        // FINAL STATE
        System.out.println("All books after deletion:");
        bookDao.findAll().forEach(System.out::println);
    }
}
