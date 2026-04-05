package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(null, "The witcher", new BigDecimal("50.00"));
        bookDao.create(book);
        System.out.println("Book " + book + " has been added.");

        Optional<Book> foundBook = bookDao.findById(book.getId());
        foundBook.ifPresent(b -> System.out.println("Found: " + b));

        book.setTitle("The witcher 2");
        bookDao.update(book);
        System.out.println("Updated: " + bookDao.findById(book.getId()).get());

        System.out.println("All books: " + bookDao.findAll());

        boolean isDeleted = bookDao.deleteById(book.getId());
        System.out.println("Has been deleted? " + isDeleted);
    }
}
