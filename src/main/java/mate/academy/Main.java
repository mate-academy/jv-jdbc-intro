package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book(null, "The Great Gatsby", BigDecimal.valueOf(10.99));
        Book savedBook = bookDao.create(newBook);
        System.out.println("Added book: " + savedBook);

        System.out.println("All books in the database:");
        bookDao.findAll().forEach(System.out::println);

        savedBook.setPrice(BigDecimal.valueOf(12.99));
        bookDao.update(savedBook);
        System.out.println("Updated book: " + savedBook);

        bookDao.findById(savedBook.getId())
                .ifPresentOrElse(
                        book -> System.out.println("Found book: " + book),
                        () -> System.out.println("Book not found")
                );

        if (bookDao.deleteById(savedBook.getId())) {
            System.out.println("Deleted book with ID: " + savedBook.getId());
        } else {
            System.out.println("Failed to delete book with ID: " + savedBook.getId());
        }

        System.out.println("Books after deletion:");
        bookDao.findAll().forEach(System.out::println);
    }
}
