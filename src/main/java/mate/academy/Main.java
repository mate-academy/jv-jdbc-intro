package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(new BigDecimal(4000));
        bookDao.create(book);
        System.out.println("Created book: " + book);
        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresentOrElse(
                b -> System.out.println("Found book: " + b),
                () -> System.out.println("Book not found")
        );
        List<Book> books = bookDao.findAll();
        System.out.println("All books in DB: " + books);
        book.setTitle("Harry Potter and the Chamber of Secrets");
        book.setPrice(new BigDecimal(4500));
        book = bookDao.update(book);
        System.out.println("Updated book: " + book);
        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("Book deleted: " + isDeleted);
    }
}
