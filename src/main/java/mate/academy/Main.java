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
        book.setTitle("Effective Java");
        book.setPrice(new BigDecimal("45.50"));
        bookDao.create(book);

        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());

        book.setPrice(new BigDecimal("50.00"));
        bookDao.update(book);

        bookDao.deleteById(book.getId());
    }
}
