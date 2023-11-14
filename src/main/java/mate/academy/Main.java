package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import mate.academy.dao.BookDao;
import mate.academy.lib.DataProcessingException;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final Map<String, BigDecimal> sampleBooks;

    static {
        sampleBooks = Map.of(
                "Head First Java", new BigDecimal("38.50"),
                "Java: A Beginner’s Guide", new BigDecimal("35.68"),
                "Java for Dummies", new BigDecimal("48.0"),
                "Effective Java", new BigDecimal("42.49"),
                "Head First Design Patterns", new BigDecimal("32.38"),
                "Spring in Action", new BigDecimal("39.51"),
                "Clean Code", new BigDecimal("25.33"),
                "Test Driven: TDD and Acceptance TDD for Java Developers", new BigDecimal("28.78"),
                "Test-Driven Java Development", new BigDecimal("49.99"),
                "Thinking in Java", new BigDecimal("9.99")
        );
    }

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        final long id = 7;

        for (Map.Entry<String, BigDecimal> entry : sampleBooks.entrySet()) {
            bookDao.create(new Book(
                    entry.getKey(),
                    entry.getValue()
            ));
        }

        List<Book> oldBooks = bookDao.findAll();

        System.out.println("Books:");

        oldBooks.forEach(System.out::println);

        Book oldBook = bookDao.findById(id).orElseThrow(()
                -> new DataProcessingException("Failed to fetch data for id = " + id));

        System.out.println("\nRevising book: " + oldBook);

        oldBook.setPrice(new BigDecimal("13.37"));

        Book newBook = bookDao.update(oldBook);

        System.out.println("Revised book: " + newBook + "\n");
        System.out.println("Trying to delete book...\n");

        bookDao.deleteById(id);

        List<Book> newBooks = bookDao.findAll();

        System.out.println("Books:");

        newBooks.forEach(System.out::println);
    }
}
