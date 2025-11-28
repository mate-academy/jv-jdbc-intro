package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book("Clean Code", new BigDecimal("29.99"));
        bookDao.create(newBook);
        System.out.println("Created: " + newBook);

        bookDao.findById(newBook.getId())
                .ifPresent(book -> System.out.println("Found by id: " + book));

        System.out.println("All books:");
        bookDao.findAll().forEach(System.out::println);

        newBook.setPrice(new BigDecimal("24.99"));
        newBook.setTitle("Clean Code (Updated)");
        bookDao.update(newBook);
        System.out.println("Updated: " + newBook);

        boolean deleted = bookDao.deleteById(newBook.getId());
        System.out.println("Deleted: " + deleted);

        System.out.println("After delete, findById: "
                +
                bookDao.findById(newBook.getId()));
    }
}
