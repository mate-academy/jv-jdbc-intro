package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.db.DatabaseInitializer;
import mate.academy.lib.Injector;
import mate.academy.services.Book;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import static mate.academy.ConnectionUtil.getConnection;

public class Main {
    private static final Injector injector = new Injector("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Red Book");
        book.setPrice(50);

        bookDao.save(book);

        Book bookFromDb = bookDao.get(1L);

        Optional<Book> bookById = bookDao.findById(1L);

        Book updateBook = new Book();
        updateBook.setTitle("Ice");
        updateBook.setPrice(30);

        bookDao.update(updateBook);

    }
}
