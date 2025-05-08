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
        book.setTitle("New Book");
        book.setPrice(new BigDecimal("62323534.3"));
        bookDao.create(book);
        bookDao.findById(8L);
        Book updateBook = new Book();
        updateBook.setId(6L);
        updateBook.setTitle("Update Book");
        updateBook.setPrice(new BigDecimal("2343322224.45"));
        bookDao.update(updateBook);
        bookDao.deleteById(6L);
        bookDao.findAll();
    }
}
