package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        // initialize field values using setters or constructor
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Book 1");
        book.setPrice(BigDecimal.valueOf(33.33));
        // test other methods from BookDao
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println("Created book: " + bookDao.create(book));
        System.out.println("All books: " + bookDao.findAll());
        System.out.println("Book by ID 1: " + bookDao.findById(1L).orElse(null));
        book.setTitle("Updated Book");
        book.setPrice(BigDecimal.valueOf(44.44));
        System.out.println("Updated book: " + bookDao.update(book));
        System.out.println("Delete book with ID 1: " + bookDao.deleteByID(1L));
        System.out.println("All books after deletion: " + bookDao.findAll());
    }
}
