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
        Book book = new Book(null, "Effective Java", BigDecimal.valueOf(45.99));
        book = bookDao.create(book);
        System.out.println("Created book: " + book);

        Optional<Book> foundedBook = bookDao.findById(book.getId());
        System.out.println("Founded book: " + foundedBook.orElse(null));

        List<Book> books = bookDao.findAll();
        System.out.println("All books: " + books);

        book.setTitle("Java");
        book.setPrice(BigDecimal.valueOf(49.99));
        bookDao.update(book);
        System.out.println("Updated book: " + book);

        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("Deleted book: " + isDeleted);
    }
}
