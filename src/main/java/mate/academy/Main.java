package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("firstBook");
        firstBook.setPrice(BigDecimal.valueOf(9.99));

        Book secondBook = new Book();
        secondBook.setTitle("secondBook");
        secondBook.setPrice(BigDecimal.valueOf(4.99));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        //add first book
        bookDao.create(firstBook);

        //findAll
        System.out.println("\nAdd first book\n" + bookDao.findAll());

        //add second book
        bookDao.create(secondBook);

        //findAll
        System.out.println("\nAdd second book\n" + bookDao.findAll());

        //update where id = 1
        Book firstBookUpdate = new Book();
        firstBookUpdate.setId(1L);
        firstBookUpdate.setTitle("update");
        firstBookUpdate.setPrice(BigDecimal.valueOf(14.99));
        bookDao.update(firstBookUpdate);

        //findAll
        System.out.println("\nUPD firstBook\n" + bookDao.findAll());

        //find by id = 2
        bookDao.deleteById(2L);
        System.out.println("\nDelete book where id = 2\n" + bookDao.findAll());

        //THIS METHOD FOR FAST TEST AND CLEAN, don't use in a real application
        bookDao.dropDataBase();
        System.out.println("\nDB IS DROP!\n");
    }
}
