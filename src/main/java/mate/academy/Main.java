package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("The Lord of The Ring");
        book.setPrice(BigDecimal.valueOf(99.99));
        book = bookDao.create(book);
        System.out.println("Book " + book + " created!");

        Book fetched = bookDao.findById(book.getId()).orElseThrow();
        System.out.println("Fetched by ID: " + fetched);

        fetched.setPrice(BigDecimal.valueOf(19.99));
        Book updated = bookDao.update(fetched);
        System.out.println("Updated Book: " + updated);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books: " + allBooks);

        boolean deleted = bookDao.deleteById(updated.getId());
        System.out.println("Is Book Deleted? " + deleted);
    }
}
