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
        book.setTitle("The Pragmatic Programmer");
        book.setPrice(BigDecimal.valueOf(49.99));
        bookDao.create(book);
        System.out.println("Created: " + book);
        bookDao.findById(book.getId())
                .ifPresent(b -> System.out.println("Found by id: " + b));
        System.out.println("All books: " + bookDao.findAll());
        book.setTitle("The Pragmatic Programmer (2nd Edition)");
        book.setPrice(BigDecimal.valueOf(54.99));
        bookDao.update(book);
        System.out.println("Updated: " + book);
        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Deleted: " + deleted);
    }
}
