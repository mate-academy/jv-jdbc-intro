package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("NewTitle");
        book.setPrice(BigDecimal.valueOf(40));
        Book bookUpdated = bookDao.create(book);
        System.out.println("Created: " + bookUpdated);

        Optional<Book> findById = bookDao.findById(book.getId());
        System.out.println("Found: " + findById);

        List<Book> list = bookDao.findAll();
        System.out.println("All lines: " + list);

        book.setTitle("Updated title");
        bookDao.update(book);
        System.out.println("Updated: " + book);

        boolean deleted = bookDao.deleteById(book.getId());
        System.out.println("Deleted: " + deleted);
    }
}
