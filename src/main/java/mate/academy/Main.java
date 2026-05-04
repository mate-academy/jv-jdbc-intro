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

        book.setTitle("Clean code");
        book.setPrice(new BigDecimal("25.50"));

        Book saved = bookDao.create(book);
        System.out.println("Created book: " + saved);

        System.out.println("Found book by id: " + bookDao.findById(saved.getId()));

        System.out.println(bookDao.findAll());

        saved.setPrice(new BigDecimal("30.00"));
        System.out.println("Updated book: " + bookDao.update(saved));

        System.out.println("Deleted book by id: " + bookDao.deleteById(saved.getId()));
    }
}
