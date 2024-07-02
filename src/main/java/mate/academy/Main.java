package mate.academy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.impl.BookDaoImpl;
import mate.academy.dao.util.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        try (Connection connection = ConnectionUtil.getConnection()) {
            BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
            bookDao = new BookDaoImpl(connection);

            Book newBook = new Book();
            newBook.setTitle("Java");
            newBook.setPrice(new BigDecimal("21.00"));
            Book createdBook = bookDao.create(newBook);
            System.out.println("Created: " + createdBook);

            Optional<Book> foundBook = bookDao.findById(createdBook.getId());
            foundBook.ifPresent(book -> System.out.println("Found: " + book));

            System.out.println("All books: " + bookDao.findAll());

            createdBook.setPrice(new BigDecimal("40.00"));
            Book updatedBook = bookDao.update(createdBook);
            System.out.println("Updated: " + updatedBook);

            boolean deleted = bookDao.deleteById(createdBook.getId());
            System.out.println("Deleted: " + deleted);
        } catch (SQLException e) {
            throw new RuntimeException("Couldn't establish database connection", e);
        }
    }
}
