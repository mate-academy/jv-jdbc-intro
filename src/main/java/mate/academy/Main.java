package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // Get an instance of the LiteraryFormatService
        BookService bookService = (BookService)
                injector.getInstance(BookService.class);
        // Print all literary formats
        bookService.getAllBooks().forEach(System.out::println);
        // Create a new book
        Book book = new Book();
        book.setTitle("Kobzar");
        book.setPrice(BigDecimal.valueOf(100));
        // Set the literary format of the book using the LiteraryFormatService
        book.setPrice(BigDecimal.valueOf(50));
        bookService.createBook(book);
        Book book1 = new Book();
        book.setTitle("Java");
        book.setPrice(BigDecimal.valueOf(200));
        bookService.createBook(book1);
        bookService.getAllBooks();
        Book book2 = new Book();
        book2.setId(2L);
        book2.setPrice(BigDecimal.valueOf(150));
        bookService.updateBook(book2);
        bookService.getAllBooks();
        bookService.deleteBook(3L);
    }
}
