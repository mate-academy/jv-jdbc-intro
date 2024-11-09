package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Dao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.Optional;

import static mate.academy.db.DatabaseInitializer.initializeDatabaseScript;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        initializeDatabaseScript();

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book redBook = new Book();
        redBook.setTitle("Red Book");
        redBook.setPrice(BigDecimal.valueOf(50));
        bookDao.create(redBook);

        bookDao.deleteById(1l);
    }
}
