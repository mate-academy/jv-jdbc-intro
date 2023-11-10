package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("BookTitle");
        book.setPrice(new BigDecimal(152));
        bookDao.create(book);
    }
}
