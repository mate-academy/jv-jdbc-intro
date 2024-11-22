package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("\n *** CREATE AND READ FUNCTIONS TEST - create and findById *** \n");

        // CREATE
        Book book1 = new Book();
        book1.setTitle("Biały zamek");
        book1.setPrice(BigDecimal.valueOf(20));

        Book book2 = new Book();
        book2.setTitle("Biały zamek");
        book2.setPrice(BigDecimal.valueOf(20));

        Book book3 = new Book();
        book3.setTitle("Biały zamek");
        book3.setPrice(BigDecimal.valueOf(20));

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);

        // READ
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findById(3L));

        System.out.println("\n *** UPDATE AND READ FUNCTIONS TEST - update and findAll *** \n");

        // UPDATE
        book1 = bookDao.findById(1L).get();
        book2 = bookDao.findById(2L).get();
        book3 = bookDao.findById(3L).get();

        book1.setPrice(BigDecimal.valueOf(50));

        book2.setTitle("Anna In w grobowcach swiata");
        book2.setPrice(BigDecimal.valueOf(34));

        book3.setTitle("Bialosc");
        book3.setPrice(BigDecimal.valueOf(30));

        bookDao.update(book1);
        bookDao.update(book2);
        bookDao.update(book3);

        System.out.println(bookDao.findAll());

        // DELETE
        System.out.println("\n *** DELETE AND READ FUNCTIONS TEST - delete and findAll *** \n");
        bookDao.delete(3L);
        System.out.println(bookDao.findAll());

        System.out.println("\n *** DELETE FUNCTIONS TEST ON NOT EXISTING ID - delete *** \n");
        System.out.println(bookDao.delete(4L));
    }
}
