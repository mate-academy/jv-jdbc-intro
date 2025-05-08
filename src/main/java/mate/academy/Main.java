package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDaoImpl.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(9.99));
        book.setTitle("Return of the King");
        bookDao.create(book);
        bookDao.findById(1L);
        bookDao.findAll();
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Test title");
        updatedBook.setPrice(BigDecimal.valueOf(10.99));
        bookDao.update(updatedBook);
        bookDao.deleteById(1L);
    }
}
