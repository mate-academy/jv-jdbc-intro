package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("1984", BigDecimal.valueOf(200L));
        Book book2 = new Book("Small prince", BigDecimal.valueOf(180L));
        // initialize field values using setters or constructor
        bookDao.create(book);
        bookDao.create(book2);

        System.out.println(bookDao.findById(book2.getId()).get());

        book2.setPrice(BigDecimal.valueOf(230L));
        bookDao.update(book2);

        System.out.println(bookDao.findAll().toString());
        bookDao.deleteById(book2.getId());

    }
}
