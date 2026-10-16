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
        book.setPrice(new BigDecimal("49.99"));
        bookDao.create(book);
        System.out.println("Created: " + book);

        System.out.println("Found by id: " + bookDao.findById(book.getId()));

        book.setPrice(new BigDecimal("39.99"));
        System.out.println("Updated: " + bookDao.update(book));

        System.out.println("All books: " + bookDao.findAll());

        System.out.println("Deleted: " + bookDao.deleteById(book.getId()));
        System.out.println("Found after delete: " + bookDao.findById(book.getId()));
    }
}
