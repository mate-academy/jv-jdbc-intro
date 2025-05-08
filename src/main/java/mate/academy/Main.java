package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.dao");
    public static final String BOOK_NAME = "Dune";
    public static final int PRICE = 100;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book dune = new Book(BOOK_NAME, BigDecimal.valueOf(PRICE));

        bookDao.create(dune);
        System.out.println(bookDao.findAll());

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Dune2");
        book.setPrice(BigDecimal.valueOf(500));

        bookDao.update(book);
        System.out.println(bookDao.findAll());

        bookDao.deleteById(1L);
        System.out.println(bookDao.findAll());
    }
}
