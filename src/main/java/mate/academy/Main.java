package mate.academy;

import java.math.BigDecimal;
import java.math.BigInteger;
import mate.academy.dao.BookDao;
import mate.academy.lib.DataBaseInitializer;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        DataBaseInitializer.initializeDatabase();
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookToCreate = new Book(
                "Coding in Java",
                BigDecimal.TEN
        );
        Book bookToUpdate = new Book(
                BigInteger.ONE,
                "Writing in Java",
                BigDecimal.TEN
        );
        Book extraBook = new Book(
                "Slaving in Java",
                BigDecimal.TEN
        );
        // initialize field values using setters or constructor
        bookDao.create(bookToCreate);
        bookDao.create(extraBook);
        bookDao.findById(1L);
        bookDao.findAll();
        Book updated = bookDao.update(bookToUpdate);
        bookDao.deleteById(1L);
        System.out.println(updated.equals(bookToUpdate) + " - update result");
        // test other methods from BookDao
    }
}
