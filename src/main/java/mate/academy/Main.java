package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Book firstBook = new Book();
        firstBook.setTitle("Tom Sawyer1");
        firstBook.setPrice(BigDecimal.valueOf(10));

        Book secondBook = new Book();
        secondBook.setTitle("Tom Sawyer2");
        secondBook.setPrice(BigDecimal.valueOf(15));

        Book thirdBook = new Book();
        thirdBook.setTitle("Tom Sawyer3");
        thirdBook.setPrice(BigDecimal.valueOf(20));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        Book book = bookDao.findById(35L).get();

        book.setPrice(BigDecimal.valueOf(17));
        bookDao.update(book);

        List<Book> allBooks = bookDao.findAll();

        bookDao.deleteById(36L);
    }
}
