package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        // Initialize data
        List<Book> books = initializeBooks();
        books.forEach(book -> System.out.println("Created record in DB: " + bookDao.create(book)));

        // Fetch all books once and reuse the list
        List<Book> allBooks = bookDao.findAll();
        System.out.println(System.lineSeparator() + "Records in DB:");
        allBooks.forEach(System.out::println);

        // Update a specific book
        allBooks.stream()
                .filter(b -> Objects.equals(b.getTitle(), "Effective Java"))
                .findFirst()
                .ifPresent(updatedBook -> {
                    updatedBook.setTitle("SQL in 10 Minutes");
                    updatedBook.setPrice(BigDecimal.valueOf(24.99));
                    System.out.println(System.lineSeparator()
                            + "Updated in db: " + bookDao.update(updatedBook));
                });

        // Delete all books
        allBooks.forEach(book -> System.out.println("Deleted record from DB: "
                + bookDao.deleteById(book.getId())));
    }

    private static List<Book> initializeBooks() {
        return List.of(
                new Book("Core Java Volume I – Fundamentals", BigDecimal.valueOf(29.99)),
                new Book("Effective Java", BigDecimal.valueOf(19.99)),
                new Book("Java - The Complete Reference", BigDecimal.valueOf(35.45))
        );
    }
}
