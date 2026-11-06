package mate.academy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.dao.ConnectionUtil;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = ConnectionUtil.getConnection()) {
            System.out.println("Connected successfully!");
        } catch (SQLException e) {
            throw new DataProcessingException("Could not connect to database!", e);
        }

        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Clean Code");
        book.setPrice(new BigDecimal("500"));
        bookDao.create(book);
        System.out.println(book.getId());

        Optional<Book> foundBook = bookDao.findById(book.getId());
        System.out.println(foundBook);

        Optional<Book> missingBook = bookDao.findById(999L);
        System.out.println(missingBook);

        List<Book> books = bookDao.findAll();
        System.out.println(books.size());

        book.setTitle("Effective Java");
        book.setPrice(new BigDecimal("700"));
        Book updatedBook = bookDao.update(book);
        System.out.println(updatedBook.getTitle());

        boolean firstDelete = bookDao.deleteById(book.getId());
        System.out.println(firstDelete);

        boolean secondDelete = bookDao.deleteById(book.getId());
        System.out.println(secondDelete);
    }
}
