package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("StarWars");
        book.setPrice(new BigDecimal(100));

        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());

        book.setPrice(new BigDecimal(120));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(book.getId()));

    }
}
