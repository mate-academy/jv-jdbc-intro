package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.models.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 =
                bookDao.create(new Book("Robinson Crusoe", new BigDecimal(10)));
        Book book2 =
                bookDao.create(new Book("Cinderella", new BigDecimal(20)));
        Book book3 =
                bookDao.create(new Book("Treasure island", new BigDecimal(25)));
        bookDao.findAll().forEach(System.out::println);
        book1.setPrice(new BigDecimal(77));
        System.out.println(bookDao.findById(book2.getId()));
        bookDao.deleteById(book2.getId());
        bookDao.update(book1);
        bookDao.findAll().forEach(System.out::println);
    }
}
