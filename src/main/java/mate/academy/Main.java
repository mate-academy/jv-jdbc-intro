package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // CREATE
        Book book1 = new Book("Clean Code", new BigDecimal("29.99"));
        Book book2 = new Book("Effective Java", new BigDecimal("39.99"));
        bookDao.create(book1);
        bookDao.create(book2);
        System.out.println("Created: " + book1);
        System.out.println("Created: " + book2);

        // FIND BY ID
        Optional<Book> found = bookDao.findById(book1.getId());
        System.out.println("Found by id: " + found);

        // FIND ALL
        List<Book> all = bookDao.findAll();
        System.out.println("All books: " + all);

        // UPDATE
        book1.setTitle("Clean Code (Updated)");
        book1.setPrice(new BigDecimal("24.99"));
        bookDao.update(book1);
        System.out.println("Updated: " + book1);

        // DELETE
        boolean deleted = bookDao.deleteById(book2.getId());
        System.out.println("Deleted book2: " + deleted);
        System.out.println("All after delete: " + bookDao.findAll());
    }
}
