package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {

        try {
            Injector injector = Injector.getInstance("mate.academy");
            BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

            Book book = new Book("Harry Potter and The Order Of the Phoenix",
                    BigDecimal.valueOf(19.99));

            book = bookDao.create(book);
            System.out.println("Created: " + book);

            bookDao.findById(book.getId()).ifPresent(System.out::println);

            book.setPrice(BigDecimal.valueOf(10.99));
            bookDao.update(book);
            System.out.println("Updated: " + book);

            List<Book> books = bookDao.findAll();
            System.out.println("Found: " + books);

            boolean deleted = bookDao.deleteById(book.getId());
            System.out.println("Deleted: " + deleted);
        } catch (SQLException e) {
            throw new RuntimeException("Error during DB operations: " + e.getMessage());
        }
    }
}
