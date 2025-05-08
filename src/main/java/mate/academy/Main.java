package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book createBook = new Book();
        createBook.setTitle("King Arthur");
        createBook.setPrice(BigDecimal.valueOf(355));

        Book updateBook = new Book();
        updateBook.setId(2L);
        updateBook.setTitle("Robin");
        updateBook.setPrice(BigDecimal.valueOf(500));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // Create
        System.out.println(bookDao.create(createBook));
        // Update
        System.out.println(bookDao.update(updateBook));
        // Delete
        System.out.println(bookDao.deleteById(7L));
        // Read
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
    }
}
