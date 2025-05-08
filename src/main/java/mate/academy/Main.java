package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("second book");
        book.setPrice(BigDecimal.valueOf(200));
        Book bookFromDB = bookDao.create(book);
        System.out.println(bookFromDB);

        System.out.println("All data from DB: " + bookDao.findAll());
        System.out.println("First item from DB: " + bookDao.findById(1L));

        book.setId(1L);
        book.setTitle("Book from Updating");
        book.setPrice(BigDecimal.valueOf(299));
        System.out.println("Book was updated " + bookDao.update(book));

        System.out.println("Has id = 1 deleted? - " + bookDao.deleteById(1L));
    }
}
