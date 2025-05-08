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
        firstBook.setTitle("ABC");
        firstBook.setPrice(BigDecimal.valueOf(300));
        Book book = bookDao.create(firstBook);
        System.out.println(book);
        System.out.println(bookDao.findAll());
        firstBook.setPrice(BigDecimal.valueOf(350));
        bookDao.update(firstBook);
        System.out.println(bookDao.findById(1L));
        bookDao.deleteById(1L);
    }
}
