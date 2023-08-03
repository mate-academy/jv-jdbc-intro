package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = bookDao.create(new Book("Interesting BOOK", BigDecimal.valueOf(777)));
        System.out.println(book.getId());
        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());
        book.setTitle("BORING");
        bookDao.update(book);
        System.out.println(bookDao.deleteById(book.getId()));

    }
}
