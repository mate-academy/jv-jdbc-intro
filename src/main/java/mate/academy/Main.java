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
        book.setTitle("Effective Java");
        book.setPrice(new BigDecimal("45.99"));

        bookDao.create(book);
        System.out.println("Created book: " + book);

        Book fetchedBook = bookDao.findById(book.getId()).orElseThrow();
        System.out.println("Fetched book: " + fetchedBook);

        book.setPrice(new BigDecimal("50.99"));
        bookDao.update(book);
        System.out.println("Updated book: " + book);

        bookDao.deleteById(book.getId());
        System.out.println("Book deleted: " + book.getId());
    }
}
