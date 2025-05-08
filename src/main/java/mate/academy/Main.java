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
        book.setTitle("Harry Potter");
        book.setPrice(new BigDecimal("12312.21"));
        book = bookDao.create(book);
        System.out.println(bookDao.findById(book.getId()).get().getId());
        System.out.println(bookDao.findAll());
        book.setTitle("Count of Monte Cristo");
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(book.getId()));
    }
}
