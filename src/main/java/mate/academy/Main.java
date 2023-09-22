package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Story of Roman Empire");
        book.setPrice(BigDecimal.valueOf(350));
        BookDao bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        bookDao.create(book);
        bookDao.findById(book.getId());
        Book anotherBook = new Book();
        anotherBook.setId(2L);
        anotherBook.setTitle("OOP conceptions");
        anotherBook.setPrice(BigDecimal.valueOf(500));
        bookDao.update(anotherBook);
        bookDao.deleteById(book.getId());
        System.out.println(bookDao.findAll());
    }
}
