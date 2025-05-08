package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();

        book.setTitle("Java 8 Programming");
        book.setPrice(BigDecimal.valueOf(345));
        System.out.println(bookDao.create(book));

        Book bookUpdate = new Book();
        bookUpdate.setTitle("Java 8 Programming v1.3");
        bookUpdate.setPrice(BigDecimal.valueOf(350));
        bookUpdate.setId(book.getId());
        System.out.println(bookDao.update(bookUpdate));

        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(book.getId()));
        bookDao.deleteById(book.getId());
    }
}
