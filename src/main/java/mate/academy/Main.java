package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("Book 1");
        firstBook.setPrice(BigDecimal.valueOf(123));
        System.out.println(bookDao.create(firstBook));

        Book secondBook = new Book();
        secondBook.setTitle("Book 2");
        secondBook.setPrice(BigDecimal.valueOf(123));
        System.out.println(bookDao.create(secondBook));

        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());

        Book thirdBook = new Book();
        thirdBook.setId(1L);
        thirdBook.setTitle("Book2");
        thirdBook.setPrice(BigDecimal.valueOf(123));
        System.out.println(bookDao.update(thirdBook));

        System.out.println(bookDao.deleteById(4L));
    }
}
