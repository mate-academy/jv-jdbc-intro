package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.BookDao;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy.lib.dao");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book(1L, "Schmidt", new BigDecimal(100));
        bookDao.create(book);

        Long bookId = 1L;
        System.out.println("Book found by ID " + bookId + ": " + bookDao.findById(bookId));

        System.out.println("All books: " + bookDao.findAll());

        book.setTitle("New Author");
        bookDao.update(book);

        System.out.println("Book found by ID " + bookId + " after update: "
                + bookDao.findById(bookId));

        bookDao.deleteById(bookId);
    }
}
