package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.db.DatabaseInitializer;
import mate.academy.lib.Injector;
import mate.academy.services.Book;

import java.sql.Connection;
import java.sql.SQLException;

import static mate.academy.ConnectionUtil.getConnection;

public class Main {
    private static final Injector injector = new Injector("mate.academy");
    public static void main(String[] args) {
        DatabaseInitializer.initializeDatabaseScript();

        try (Connection connection = getConnection()) {
            System.out.println("Connection successful!");
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book redBook = new Book();
        redBook.setTitle("Red Book");
        redBook.setPrice(50);

        Book saveRedBook = bookDao.save(redBook);

        System.out.println(bookDao.get(1L));
    }
}
