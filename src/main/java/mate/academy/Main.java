package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {

    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // CREATE
        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(50));

        bookDao.create(book);

        System.out.println("Created book: " + book);

        // FIND BY ID
        bookDao.findById(book.getId())
                .ifPresent(foundBook ->
                        System.out.println("Found book: " + foundBook));

        // FIND ALL
        System.out.println("All books:");
        bookDao.findAll()
                .forEach(System.out::println);

        // UPDATE
        book.setTitle("Clean Code Updated");
        book.setPrice(BigDecimal.valueOf(60));

        bookDao.update(book);

        System.out.println("Updated book:");
        bookDao.findById(book.getId())
                .ifPresent(System.out::println);

        // DELETE
        boolean deleted = bookDao.deleteById(book.getId());

        System.out.println("Book deleted: " + deleted);

        // CHECK AFTER DELETE
        System.out.println("Books after delete:");
        bookDao.findAll()
                .forEach(System.out::println);
    }
}
