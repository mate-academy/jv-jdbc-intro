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
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(399.99));
        bookDao.create(book);
        bookDao.findById(book.getId());
        book.setTitle("The Song of Ice and Fire");
        book.setPrice(BigDecimal.valueOf(1999));
        Book updatedBook = bookDao.update(book);
        bookDao.findById(updatedBook.getId());
        bookDao.findAll();
        bookDao.deleteById(updatedBook.getId());
    }
}
