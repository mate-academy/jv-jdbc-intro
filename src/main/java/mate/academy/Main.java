package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        Book book1 = new Book();
        Book book2 = new Book();
        book.setTitle("The Lord Of The Rings");
        book1.setTitle("Harry Potter");
        book2.setTitle("The Lord Of the Rings Two Towels");
        book.setPrice(BigDecimal.valueOf(200));
        book1.setPrice(BigDecimal.valueOf(300));
        book2.setPrice(BigDecimal.valueOf(700));
        bookDao.create(book2);
        bookDao.create(book1);
        bookDao.create(book);
        System.out.println(book);
        book.setPrice(BigDecimal.valueOf(500));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.deleteById(book.getId()));
    }
}
