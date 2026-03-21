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
        book.setPrice(new BigDecimal(1000));
        book.setTitle("War");
        Book createdBook = bookDao.create(book);
        System.out.println(createdBook);
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());
        createdBook.setPrice(new BigDecimal(100000000));
        bookDao.update(createdBook);
        System.out.println(bookDao.deleteById(2L));
    }
}
