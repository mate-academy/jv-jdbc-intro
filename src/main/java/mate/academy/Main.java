package mate.academy;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book newBook = new Book("Clean Code", new BigDecimal("19.99"));
        bookDao.create(newBook);

        Optional<Book> foundBook = bookDao.findById(newBook.getId());
        foundBook.ifPresent(System.out::println);

        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        newBook.setPrice(new BigDecimal("17.99"));
        bookDao.update(newBook);
        System.out.println(newBook);

        boolean isDeleted = bookDao.deleteById(newBook.getId());
        System.out.println("Deleted: " + isDeleted);
    }
}

class TestProperties {
    public static void main(String[] args) {
        try (InputStream input = TestProperties.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.out.println("File not found");
                return;
            }
            Properties props = new Properties();
            props.load(input);
            System.out.println("Loaded DB URL: " + props.getProperty("db.url"));
            System.out.println("Loaded DB Username: " + props.getProperty("db.username"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

