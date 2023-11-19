package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {

        final Injector injector = Injector.getInstance("mate.academy");
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("new");
        book.setPrice(BigDecimal.valueOf(100));
        book.setId(12L);
        bookDao.create(book);
        bookDao.findById(12L);
        bookDao.update(book);
        bookDao.deleteById(12L);
        System.out.println(bookDao.findAll());
    }
}
