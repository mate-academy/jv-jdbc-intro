package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector
            .getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Book title");
        book.setPrice(new BigDecimal(1000));

        for (int i = 0; i < 3; i++) {
            System.out.println("Created book ID: "
                    + bookDao.create(book).getId());
        }
        System.out.println("Found by ID 2: " + bookDao.findById(2L));
        System.out.println("Deleted by ID 2: " + bookDao.deleteById(3L));
        System.out.println("Found all from table: " + bookDao.findAll());
        System.out.println("Updated book: " + bookDao.update(book));
    }
}
