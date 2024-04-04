package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Book #1", BigDecimal.valueOf(10));
        book = bookDao.create(book);
        System.out.println("New book was created: " + book);

        Long bookId = book.getId();
        Book bookForUpdate = new Book(bookId, "New book", BigDecimal.valueOf(123));

        System.out.println("Book #1 was updated: " + bookDao.update(bookForUpdate));
        System.out.println("All books from DB: " + bookDao.findAll());
        System.out.println("Get book by id: " + bookDao.findById(bookId));
        System.out.println("Book deleted by id: " + bookDao.deleteById(bookId));
        System.out.println("Find by id after deleting: " + bookDao.findById(bookId));
    }
}
