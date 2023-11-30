package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao dao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setPrice(BigDecimal.valueOf(20));
        System.out.println(dao.create(book));
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(45));
        book1.setTitle("Math");
        System.out.println(dao.create(book1));
        System.out.println(dao.findAll().toString());
        book1.setTitle("Java Book");
        System.out.println(dao.update(book1));
        System.out.println(dao.findById(1L));
        System.out.println(dao.findAll());
        dao.deletedById(2L);
        System.out.println(dao.findAll());
    }
}
