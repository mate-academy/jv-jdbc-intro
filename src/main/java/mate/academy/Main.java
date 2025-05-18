package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.model.Book;
import mate.academy.service.BookService;
import mate.academy.service.impl.BookServiceImpl;

public class Main {
    public static final BookDao bookDao = new BookDaoImpl();
    public static final BookService bookService = new BookServiceImpl(bookDao);

    public static void main(String[] args) {
        // adding 7 books in database
        bookService.save(new Book("Effective Java", new BigDecimal("45.00")));
        bookService.save(new Book("Java Concurrency in Practice", new BigDecimal("50.00")));
        bookService.save(new Book("Clean Code", new BigDecimal("40.00")));
        bookService.save(new Book("Head First Java", new BigDecimal("35.00")));
        bookService.save(new Book("Java: The Complete Reference", new BigDecimal("55.00")));
        bookService.save(new Book("Spring in Action", new BigDecimal("48.00")));
        bookService.save(new Book("Java Performance", new BigDecimal("52.00")));

        System.out.println("=== All books in DB after adding ===");
        List<Book> allBooks = bookService.findAll();
        allBooks.forEach(System.out::println);

        // Getting book by ID (1)
        Long firstBookId = allBooks.get(0).getId();
        Book book = bookService.get(firstBookId);
        System.out.println("=== Book with ID = " + firstBookId + " ===");
        System.out.println(book);

        // Updating firstbook price
        book.setPrice(new BigDecimal("47.99"));
        bookService.update(book);
        System.out.println("=== Book after update ===");
        System.out.println(bookService.get(firstBookId));

        // Deleting lastbook
        Long lastBookId = allBooks.get(allBooks.size() - 1).getId();
        boolean deleted = bookService.delete(bookService.get(lastBookId));
        System.out.println("=== Deleting book with ID " + lastBookId + ": "
                + (deleted ? "success" : "failed") + " ===");

        System.out.println("=== All books in DB after all operations ===");
        bookService.findAll().forEach(System.out::println);
    }
}
