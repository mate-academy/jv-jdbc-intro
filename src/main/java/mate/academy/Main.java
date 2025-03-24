package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();

        book.setTitle("1984");
        book.setPrice(BigDecimal.valueOf(14.99));

        Book created = bookDao.create(book);

        Book fetched = bookDao.findById(created.getId()).orElseThrow();
        System.out.println("Fetched by ID: " + fetched);

        fetched.setPrice(BigDecimal.valueOf(19.99));
        Book updated = bookDao.update(fetched);
        System.out.println("Updated Book: " + updated);

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All Books: " + allBooks);

        boolean deleted = bookDao.deleteById(updated.getId());
        System.out.println("Was Book Deleted? " + deleted);
    }
}
