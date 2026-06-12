package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.model.daobook.BookDao;
import mate.academy.model.daobook.BookDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();
        Book book = new Book();
        book.setAuthor("Kicia");
        book.setIsbn(UUID.randomUUID().toString().substring(0, 8));
        book.setTitle("Kocia");
        // initialize field values using setters or constructor
        book = bookDao.create(book);
        System.out.println(book.getId());
        // test other methods from BookDao
    }
}
