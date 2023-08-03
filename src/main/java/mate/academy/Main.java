package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("GOT", BigDecimal.valueOf(140L));
        bookDao.create(book);
        bookDao.findAll();
        bookDao.findById(1L);
        Book updatedBook = new Book(1L, "Harry potter", BigDecimal.valueOf(300L));
        bookDao.update(updatedBook);
        bookDao.deleteById(1L);
    }
}
