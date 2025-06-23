package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("A");
        book.setPrice(BigDecimal.valueOf(1000));
        bookDao.create(book); //create
        bookDao.findById(2L); //findById
        bookDao.findAll(); //findAll
        book.setId(5L);
        book.setTitle("B");
        book.setPrice(BigDecimal.valueOf(2000));
        bookDao.update(book); //Update
        bookDao.deleteById(10L); //Delete
    }
}
