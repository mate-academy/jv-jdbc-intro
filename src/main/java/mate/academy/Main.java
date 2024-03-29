package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(9L, "book1", new BigDecimal(24));
        System.out.println(bookDao.create(book));
        book.setTitle("first");
        book.setPrice(new BigDecimal("878.54"));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.delete(book));
        System.out.println(bookDao.findAll());
    }
}
