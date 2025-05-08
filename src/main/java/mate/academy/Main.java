package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book("A Story of yesterday", new BigDecimal("150.50"));
        bookDao.create(firstBook);
        Book secondBook = new Book("A Love story", new BigDecimal("332.17"));
        bookDao.create(secondBook);
        Book thirdBook = new Book("The elephant tree", new BigDecimal("245.83"));
        bookDao.create(thirdBook);
        Book fourthBook = new Book("This is not a novel", new BigDecimal("157.74"));
        bookDao.create(fourthBook);

        System.out.println(bookDao.findById(2L));
        secondBook.setPrice(new BigDecimal("196.59"));
        System.out.println(bookDao.update(secondBook));
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.deleteById(3L));
        System.out.println(bookDao.findAll());
    }
}
