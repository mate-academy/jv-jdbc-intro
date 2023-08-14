package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Kobzar");
        book.setPrice(BigDecimal.valueOf(500));

        Book book1 = bookDao.create(book);
        book1.setPrice(BigDecimal.valueOf(700));
        System.out.println(book1);
        bookDao.update(book1);
        System.out.println(bookDao.findById(book1.getId()));

        bookDao.fingAll().forEach(System.out::println);
        bookDao.fingAll().forEach(b -> bookDao.deleteById(b.getId()));

    }
}
