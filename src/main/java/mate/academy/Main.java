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
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Test Book");
        book.setPrice(new BigDecimal("19.99"));
        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        System.out.println("Found book: " + foundBook.orElse(null));

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books: " + allBooks);

        createdBook.setTitle("Updated Test Book");
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(updatedBook.getId());
        System.out.println("Book deletion status: " + isDeleted);
    }
}
