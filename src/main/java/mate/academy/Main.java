package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("book", new BigDecimal("119.33"));
        bookDao.create(book);
        System.out.println(bookDao.findById(10L));
        for (Book element : bookDao.findAll()) {
            System.out.println(element);
        }
        Book bookUpdate = new Book(5L, "Java", new BigDecimal("999.35"));
        bookDao.update(bookUpdate);
    }
}
