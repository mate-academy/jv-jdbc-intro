package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Java");
        book.setPrice(BigDecimal.valueOf(123));
        bookDao.create(book);
        List<Book> books = bookDao.findAll();
        book.setTitle("Java New Books");
        bookDao.update(book);
        bookDao.deleteById(book.getId());
    }
}
