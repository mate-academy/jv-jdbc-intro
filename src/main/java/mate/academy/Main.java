package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        final Book firstBook = bookDao.create(new Book("First book", new BigDecimal("35.5")));
        final Book secondBook = bookDao.create(new Book("Second book", new BigDecimal("65.5")));
        final Book thirdBook = bookDao.create(new Book("Third book", new BigDecimal("15")));
        final Book fourthBook = bookDao.create(new Book("Fourth book", new BigDecimal("103.1")));

        bookDao.findAll().forEach(System.out::println);
        System.out.println();

        System.out.println(bookDao.findById(firstBook.getId()));
        System.out.println();

        firstBook.setTitle("Update first book");
        firstBook.setPrice(new BigDecimal("1000"));
        bookDao.update(firstBook);

        bookDao.findAll().forEach(System.out::println);
        System.out.println();

        bookDao.deleteById(firstBook.getId());
        bookDao.deleteById(secondBook.getId());
        bookDao.deleteById(thirdBook.getId());
        bookDao.deleteById(fourthBook.getId());

        bookDao.findAll().forEach(System.out::println);
    }
}
