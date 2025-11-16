import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("Connection OK: " + connection);
        } catch (SQLException e) {
            throw new DataProcessingException("Cannot create a connection to the DB", e);
        }

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("JDBC Intro");
        book.setPrice(BigDecimal.valueOf(25.50));
        Book createdBook = bookDao.create(book);
        System.out.println("Created book: " + createdBook);

        Optional<Book> foundBook = bookDao.findById(createdBook.getId());
        foundBook.ifPresent(value -> System.out.println("Found book by ID: " + value));

        List<Book> allBooks = bookDao.findAll();
        System.out.println("All books in DB:");
        allBooks.forEach(System.out::println);

        createdBook.setPrice(BigDecimal.valueOf(29.99));
        Book updatedBook = bookDao.update(createdBook);
        System.out.println("Updated book: " + updatedBook);

        boolean deleted = bookDao.deleteById(createdBook.getId());
        System.out.println("Deleted: " + deleted);

        List<Book> booksAfterDelete = bookDao.findAll();
        System.out.println("Books after delete:");
        booksAfterDelete.forEach(System.out::println);
    }
}
