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
        book.setTitle("1984");
        book.setPrice(BigDecimal.valueOf(300.00));
        bookDao.create(book);
        book.setId(24L);
        book.setPrice(BigDecimal.valueOf(200.00));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.findById(1L));
        for (int i = 0; i < 10; i++) {
            book = new Book();
            book.setTitle("New Title " + i);
            book.setPrice(BigDecimal.valueOf(i * 10 + 100));
            bookDao.create(book);
        }
        bookDao.deleteById(24L);
        System.out.println(bookDao.findAll());
    }
}
