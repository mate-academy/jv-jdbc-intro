package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book("Ulysses", BigDecimal.valueOf(25.99));
        Book secondBook = new Book("Don Quixote", BigDecimal.valueOf(35.50));
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.findAll();
        bookDao.findById(1L);
        firstBook.setTitle("The Great Gatsby");
        bookDao.update(firstBook);
        bookDao.deleteById(2L);
    }
}
