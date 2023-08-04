package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(299));
        book1.setTitle("Atomic Habits");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        book1.setPrice(BigDecimal.valueOf(399));
        bookDao.deleteById(1L);
    }
}
