package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Book;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("Java Basics", BigDecimal.valueOf(29.99));
        bookDao.create(book);

        System.out.println(bookDao.findById(book.getId()));
        System.out.println(bookDao.findAll());

        book.setTitle("Advanced Java");
        bookDao.update(book);

        bookDao.deleteById(book.getId());
    }
}
