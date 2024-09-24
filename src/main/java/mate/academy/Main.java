package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book(1L, "Whispers of the Forgotten Forest",
                BigDecimal.valueOf(300.00));
        Book secondBook = new Book(2L, "The Last Light of Dusk", BigDecimal.valueOf(450.00));
        Book thirdBook = new Book(3L, "Echoes of Time", BigDecimal.valueOf(500.00));
        Book updateFirstBook = new Book(1L, "Whispers of the Forgotten Forest",
                BigDecimal.valueOf(700.00));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("Book created: " + bookDao.create(firstBook));
        System.out.println("Book created: " + bookDao.create(secondBook));
        System.out.println("Book created: " + bookDao.create(thirdBook));
        System.out.println("Book updated: " + bookDao.update(updateFirstBook));
        System.out.println("Book found by ID (" + secondBook.getId() + "): "
                + bookDao.findById(secondBook.getId()));
        System.out.println("All books in the library: " + bookDao.findAll());
        System.out.println("Book deleted by ID (" + thirdBook.getId()
                + "): " + bookDao.deleteById(thirdBook.getId()));
    }
}
