package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Book number one");
        book.setPrice(BigDecimal.valueOf(11.11));
        book = bookDao.create(book);
        System.out.println(book);
        System.out.println(bookDao.findById(book.getId()));
        book.setTitle("Book number two");
        book.setPrice(BigDecimal.valueOf(22.22));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(book.getId()));
        System.out.println(bookDao.findAll());
    }
}
