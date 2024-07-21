package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("BookTitle_1");
        book.setPrice(BigDecimal.valueOf(100.00));

        bookDao.create(book);
        bookDao.findById(book.getId()).ifPresent(System.out::println);

        book.setTitle("BookTitle_test");
        book.setPrice(BigDecimal.valueOf(200.00));
        bookDao.update(book);
        bookDao.findAll().stream().forEach(System.out::println);

        System.out.println(bookDao.deleteById(book.getId()));

    }
}
