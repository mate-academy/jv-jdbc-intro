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
        book.setTitle("Sample Book");
        book.setPrice(BigDecimal.valueOf(19.99));

        bookDao.create(book);

        System.out.println("Book created with ID: " + book.getId());

        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresentOrElse(
                b -> System.out.println("Book found by ID: " + b),
                () -> System.out.println("Book not found by ID: " + book.getId())
        );

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books: " + allBooks);

        book.setTitle("Updated Title");
        book.setPrice(BigDecimal.valueOf(29.99));
        bookDao.update(book);
        System.out.println("Book updated: " + book);

        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("Book deleted: " + (isDeleted ? "Yes" : "No"));
    }
}

