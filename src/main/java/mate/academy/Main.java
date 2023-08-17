package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    final static Injector injector = Injector.getInstance("mate");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("The Witcher");
        book.setPrice(BigDecimal.valueOf(12));

        bookDao.create(book);

        System.out.println(book);

        book.setPrice(BigDecimal.valueOf(25));
        System.out.println(bookDao.update(book));

        System.out.println(bookDao.findById(book.getId()));

        System.out.println(bookDao.findAll());

        System.out.println(bookDao.deleteById(book.getId()));
    }
}
