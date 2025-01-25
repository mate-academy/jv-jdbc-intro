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
        book.setTitle("12 years a slave");
        book.setPrice(BigDecimal.valueOf(99));

        bookDao.create(book);
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(2L));

        Book newBook = new Book();
        newBook.setId(2L);
        newBook.setTitle("Device #1");
        newBook.setPrice(BigDecimal.valueOf(122));

        System.out.println(bookDao.update(newBook));
        System.out.println(bookDao.deleteById(1L));
    }
}
