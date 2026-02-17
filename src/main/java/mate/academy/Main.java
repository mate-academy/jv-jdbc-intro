package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Alphabet", BigDecimal.valueOf(15));

        bookDao.create(book);

        book.setTitle("Alphabet 2");

        bookDao.update(book);

        bookDao.findById(1L);

        bookDao.deleteById(5L);

        bookDao.findAll();
    }
}
