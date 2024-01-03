package mate.academy;

import java.math.BigDecimal;
import java.sql.SQLException;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) throws SQLException {
        BookDao dao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(10));
        book1.setTitle("pink");
        System.out.println(dao.create(book1));
        Book book2 = new Book();
        book2.setPrice(BigDecimal.valueOf(30));
        book2.setTitle("red");
        System.out.println(dao.create(book2));
        System.out.println(dao.findAll().toString());
        book2.setTitle("yellow");
        System.out.println(dao.update(book2));
        System.out.println(dao.findById(1L));
        System.out.println(dao.findAll());
        dao.deleteById(1L);
        System.out.println(dao.findAll());
    }
}
