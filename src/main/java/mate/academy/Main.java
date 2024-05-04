package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.createTable();
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Game of Thrones");
        book.setPrice(BigDecimal.valueOf(300));
        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(Long.valueOf(1)));
        System.out.println(bookDao.findAll());
        book.setPrice(BigDecimal.valueOf(400));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(Long.valueOf(1)));
    }
}
