package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book bookOne = new Book();
        bookOne.setTitle("LOST IN ENGLAND");
        bookOne.setPrice(BigDecimal.valueOf(179L));

        Book bookTwo = new Book();
        bookTwo.setTitle("1984");
        bookTwo.setPrice(BigDecimal.valueOf(569L));

        Book bookThree = new Book();
        bookThree.setTitle("MOON SHOWS REALITY");
        bookThree.setPrice(BigDecimal.valueOf(199L));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println("Book created: " + bookDao.create(bookOne));
        System.out.println("Book created: " + bookDao.create(bookTwo));
        System.out.println("Book created: " + bookDao.create(bookThree));

        bookOne.setTitle("LOST IN BRITAIN");
        System.out.println("Book updated: " + bookDao.update(bookOne));

        bookTwo.setPrice(BigDecimal.valueOf(499L));
        System.out.println("Book updated: " + bookDao.update(bookTwo));

        System.out.println("Found bookOne: " + bookDao.findById(1L));
        System.out.println("Found bookTwo: " + bookDao.findById(2L));

        System.out.println("Book list: " + bookDao.findAll());

        bookDao.deleteById(3L);
        System.out.println("Book list after deleted book by 3L index: " + bookDao.findAll());
    }
}
