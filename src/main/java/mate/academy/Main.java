package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao daoBook = (BookDao) INJECTOR.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("19.99"));
        Book createdBook = daoBook.create(book);
        System.out.println("Created book: " + createdBook);

        Optional<Book> foundBook = daoBook.findById(createdBook.getId());
        System.out.println("Found book: " + foundBook.orElse(null));

        List<Book> allBooks = daoBook.findAll();
        System.out.println("All books: " + allBooks);

        createdBook.setTitle("Updated Test Book");
        Book updatedBook = daoBook.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = daoBook.deleteById(updatedBook.getId());
        System.out.println("Book deletion status: " + isDeleted);
    }
}
