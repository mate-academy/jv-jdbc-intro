package mate.academy;

import java.math.BigDecimal;
import java.sql.Connection;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.util.ConnectionUtil;
import mate.academy.util.DatabaseInit;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            DatabaseInit.executeSqlScript("init_db.sql", connection);
        } catch (Exception e) {
            throw new RuntimeException("Error while initializing DB", e);
        }

        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Book_1");
        book1.setPrice(BigDecimal.valueOf(11.11));

        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Book_2");
        book2.setPrice(BigDecimal.valueOf(22.22));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.printf("Created: %s, %s%n", bookDao.create(book1), bookDao.create(book2));
        System.out.printf("Books in DB: %s%n", bookDao.findAll());
        System.out.printf("Book by ID 1: %s%n", bookDao.findById(1L).orElse(null));
        book1.setTitle("Changed Title");
        book1.setPrice(BigDecimal.valueOf(0.0));
        System.out.printf("Updated first book: %s%n", bookDao.update(book1));
        System.out.printf("Deleted book with ID 1: %s%n", bookDao.deleteById(1L));
        System.out.printf("Remaining books after deletion: %s%n", bookDao.findAll());
    }
}
