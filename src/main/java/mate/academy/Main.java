package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book("Clear code", BigDecimal.valueOf(200.00));
        Book secondBook = new Book("Effective java", BigDecimal.valueOf(356.47));
        Book thirdBook = new Book("Head first", BigDecimal.valueOf(124.45));
        // initialize field values using setters or constructor
        Book firstBookDB = bookDao.create(firstBook);
        Book secondBookDB = bookDao.create(secondBook);
        Book thirdBookDB = bookDao.create(thirdBook);
        // test other methods from BookDao
        Book bookByID = bookDao.findById(thirdBookDB.getId())
                .orElseThrow(() -> new RuntimeException(
                        "Can't find book by id" + thirdBookDB.getId())
                );
        System.out.println(bookByID);
        bookDao.findAll().forEach(System.out::println);
        Book updateBook = bookDao.update(new Book(
                firstBookDB.getId(), "Spring in action", BigDecimal.valueOf(93.85))
        );
        System.out.println(updateBook);
        bookDao.deleteById(thirdBookDB.getId());
        bookDao.findAll().forEach(System.out::println);
    }
}
