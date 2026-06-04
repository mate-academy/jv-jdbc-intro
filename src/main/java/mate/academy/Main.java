package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("The Great Gatsby");
        book.setPrice(new BigDecimal("15.99"));
        bookDao.create(book);
        System.out.println("Created book: " + book);

        Book foundBook = bookDao.findById(book.getId()).orElse(null);
        System.out.println("Found book by id: " + foundBook);

        System.out.println("All books: " + bookDao.findAll());

        foundBook.setPrice(new BigDecimal("19.99"));
        bookDao.update(foundBook);
        System.out.println("Updated book: " + bookDao.findById(foundBook.getId()).orElse(null));

        bookDao.deleteById(foundBook.getId());
        System.out.println("All books after deletion: " + bookDao.findAll());
    }
}
