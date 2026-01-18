package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(300));
        book.setTitle("Golden rabbit");
        System.out.println(bookDao.create(book));
        Book bookUpdate = new Book();
        bookUpdate.setTitle("Chocolate factory");
        bookUpdate.setPrice(BigDecimal.valueOf(200));
        bookUpdate.setId(4L);
        System.out.println(bookDao.update(bookUpdate));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(2L));
        bookDao.deleteById(10L);

    }
}
