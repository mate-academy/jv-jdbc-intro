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
        book.setTitle("Romeo and Juliet");
        book.setPrice(BigDecimal.valueOf(300));
        bookDao.create(book);
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(6L));
        book.setPrice(BigDecimal.valueOf(400));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(6L));
    }
}
