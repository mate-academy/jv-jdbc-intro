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
        Book book1 = new Book();
        book1.setTitle("The Great Gatsby");
        book1.setPrice(BigDecimal.valueOf(19.99));
        book1 = bookDao.create(book1);
        System.out.println("Created book: " + book1);

        Book book2 = new Book();
        book2.setTitle("1984");
        book2.setPrice(BigDecimal.valueOf(15.50));
        book2 = bookDao.create(book2);
        System.out.println("Created book: " + book2);

        Optional<Book> foundBook = bookDao.findById(book1.getId());
        foundBook.ifPresent(book -> System.out.println("Found book by ID: " + book));

        book1.setTitle("The Great Gatsby - Updated");
        book1.setPrice(BigDecimal.valueOf(21.99));
        Book updatedBook = bookDao.update(book1);
        System.out.println("Updated book: " + updatedBook);

        List<Book> books = bookDao.findAll();
        System.out.println("All books: " + books);

        boolean isDeleted = bookDao.deleteById(book2.getId());
        System.out.println("Book with ID " + book2.getId() + " deleted " + isDeleted);

        List<Book> remainingBooks = bookDao.findAll();
        System.out.println("Remaining books after deletion: " + remainingBooks);
    }
}
