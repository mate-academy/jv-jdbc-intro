package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        List<Book> books = List.of(
                new Book("The Hunger Games: Coding with Java Edition",
                        BigDecimal.valueOf(10.00)),
                new Book("The Hobbit: An Unexpected Java Error",
                        BigDecimal.valueOf(200.00)),
                new Book("Jurassic Park: The Dinosaur Database",
                        BigDecimal.valueOf(300.33)),
                new Book("Harry Potter and the Chamber of Java Beans",
                        BigDecimal.valueOf(1500.99)),
                new Book("The Terminator: Rise of the Java Machines",
                        BigDecimal.valueOf(100.49)),
                new Book("Star Wars: The Phantom Bytecode Menace",
                        BigDecimal.valueOf(365.80)),
                new Book("Java Documentation",
                        BigDecimal.valueOf(0.00))
        );

        List<Book> createdBooks = books.stream()
                .map(bookDao::create)
                .toList();

        Book book = bookDao.findById(createdBooks.get(1).getId())
                .orElseThrow(() -> new RuntimeException("Book was not found"));
        System.out.println(book);

        bookDao.findAll().forEach(System.out::println);

        book.setTitle("Some other title");
        book.setPrice(BigDecimal.valueOf(10));
        Book updateBook = bookDao.update(book);
        System.out.println(updateBook);

        bookDao.deleteById(createdBooks.get(0).getId());
    }
}
