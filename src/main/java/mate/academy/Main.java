package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");
    private static final Long ID = 2L;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setId(1L);
        firstBook.setTitle("White Fang");
        firstBook.setPrice(BigDecimal.valueOf(20.00));

        Book secondBook = new Book();
        secondBook.setId(2L);
        secondBook.setTitle("Sherlock Holmes");
        secondBook.setPrice(BigDecimal.valueOf(28.40));

        bookDao.create(firstBook);
        bookDao.findById(ID);
        bookDao.update(secondBook);
        bookDao.deleteById(ID);
        bookDao.findAll();
    }
}
