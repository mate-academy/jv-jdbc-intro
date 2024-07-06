package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setTitle("SQL for Data Analysis");
        book1.setPrice(BigDecimal.valueOf(500));
        book1 = bookDao.create(book1);
        Book book2 = new Book();
        book2.setTitle("Effective Java");
        book2.setPrice(BigDecimal.valueOf(800));
        book2 = bookDao.create(book2);
        System.out.println(bookDao.findById(book2.getId()));
        System.out.println(bookDao.getAll());
        System.out.println(bookDao.delete(book1.getId()));
        book2.setTitle("Joshua Bloch: Effective Java");
        book2.setPrice(BigDecimal.valueOf(850));
        bookDao.update(book2);
        System.out.println(bookDao.getAll());

    }
}
