package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book();
        newBook.setTitle("Java Programming");
        newBook.setPrice(new BigDecimal("59.99"));
        Book createdBook = bookDao.create(newBook);
        System.out.println("Created book: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        foundBook.ifPresent(book -> System.out.println("Found book: " + book));

        List<Book> books = bookDao.findAll();
        System.out.println("All books: " + books);

        createdBook.setTitle("Advanced Java Programming");
        createdBook.setPrice(new BigDecimal("69.99"));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean isDeleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Book deleted: " + isDeleted);
    }
}
