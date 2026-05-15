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
        book.setTitle("Clean Code");
        book.setPrice(BigDecimal.valueOf(29.99));
        bookDao.create(book);
        System.out.println("Created: " + book);

        bookDao.findById(book.getId())
                .ifPresent(b -> System.out.println("Found by id: " + b));

        System.out.println("All books: " + bookDao.findAll());

        book.setTitle("Clean Code Updated");
        book.setPrice(BigDecimal.valueOf(39.99));
        bookDao.update(book);
        System.out.println("Updated: " + book);

        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Deleted: " + deleted);
    }
}
