package mate.academy;

import java.math.BigDecimal;
import mate.academy.models.Book;
import mate.academy.services.BookService;
import mate.academy.services.BookServiceImpl;

public class Main {

    public static void main(String[] args) {
        BookService bookService = new BookServiceImpl();

        // Create
        Book bookToCreate = new Book();
        bookToCreate.setTitle("Harry");
        bookToCreate.setPrice(BigDecimal.valueOf(666));

        Book createdBook = bookService.create(bookToCreate);
        System.out.println("created book: " + createdBook);

        // Update
        Book updatedBook = new Book();
        updatedBook.setId(createdBook.getId());
        updatedBook.setTitle("Updated Book");
        updatedBook.setPrice(BigDecimal.valueOf(777));
        bookService.update(updatedBook);

        // Read
        System.out.println("Find by id: " + bookService.findById(updatedBook.getId()));
        System.out.println("Read all books:" + bookService.findAll());

        // Delete
        System.out.println("Deleted book: "
                + bookService.deleteById(createdBook.getId()));

        System.out.println("After deleting: " + bookService.findAll());

    }
}
