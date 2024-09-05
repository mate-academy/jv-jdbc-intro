package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book("The Perfect Shot", BigDecimal.valueOf(100));
        Book secondBook = new Book("Modern Java", BigDecimal.valueOf(350));
        Book thirdBook = new Book("Atomic Habits", BigDecimal.valueOf(220));
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);
        bookDao.findAll();
        thirdBook.setPrice(BigDecimal.valueOf(250));
        bookDao.update(thirdBook);
        bookDao.findById(2L).get();
        bookDao.deleteById(1L);
    }
}
