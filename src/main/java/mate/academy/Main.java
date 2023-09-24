package mate.academy;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import mate.academy.service.BookService;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
//        BookService bookService = (BookService) injector.getInstance(BookService.class);

//        Book addBook = new Book("The Sun Also Rises", BigDecimal.valueOf(450)); //works
//        Book book = bookDao.create(addBook);
//        System.out.println(book);

//        Optional<Book> byId = bookDao.findById(3L); //works but Optional
//        System.out.println(byId);

//        List<Book> all = bookDao.findAll(); //works
//        System.out.println(all);

//        Book theLittlePrince = bookDao.update(new Book(3L, "The Little Prince", BigDecimal.valueOf(100)));
//        System.out.println(theLittlePrince);

        System.out.println(bookDao.deleteById(7L));

    }
}
