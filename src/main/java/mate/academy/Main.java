package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("Harry Potter");
        firstBook.setPrice(BigDecimal.valueOf(100));
        firstBook = bookDao.create(firstBook);
        Book secondBook = new Book();
        secondBook.setTitle("Witcher");
        secondBook.setPrice(BigDecimal.valueOf(120));
        secondBook = bookDao.create(secondBook);
        System.out.println(bookDao.findById(firstBook.getId()));
        System.out.println(bookDao.findById(secondBook.getId()));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.deleteById(firstBook.getId()));
        secondBook.setPrice(BigDecimal.valueOf(140));
        bookDao.update(secondBook);
        System.out.println(bookDao.findAll());
    }
}
