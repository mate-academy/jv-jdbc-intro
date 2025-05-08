package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("Creating first book:");
        System.out.println(bookDao.create(new Book("Book1", BigDecimal.valueOf(1.99))));

        System.out.println("\nFinding first book by id:");
        System.out.println(bookDao.findById(1L));

        System.out.println("\nFinding non-existing book:");
        System.out.println(bookDao.findById(111L));

        System.out.println("\nCreating second book and finding all books by id:");
        bookDao.create(new Book("Book2", BigDecimal.valueOf(2.99)));
        bookDao.findAll().forEach(System.out::println);

        System.out.println("\nUpdating first book:");
        bookDao.update(new Book(1L, "Book1Updated", BigDecimal.valueOf(3.99)));
        bookDao.findAll().forEach(System.out::println);

        System.out.println("\nDeleting first book:");
        System.out.println(bookDao.deleteById(1L));

        System.out.println("\nEnsuring that first book was deleted:");
        bookDao.findAll().forEach(System.out::println);
    }
}
