package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book("First Book", BigDecimal.valueOf(120));
        Book secondBook = new Book("Second Book", BigDecimal.valueOf(220));
        Book thirdBook = new Book("Third Book", BigDecimal.valueOf(110));

        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        bookDao.findById(firstBook.getId());
        bookDao.findById(secondBook.getId());

        bookDao.findAll();

        thirdBook.setPrice(BigDecimal.valueOf(420));
        bookDao.update(thirdBook);
        bookDao.deleteById(thirdBook.getId());
    }
}
