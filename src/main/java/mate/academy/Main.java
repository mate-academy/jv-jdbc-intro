package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector
            = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("SonySon", BigDecimal.valueOf(125));
        Book book2 = new Book("OneInch", BigDecimal.valueOf(500));
        Book book3 = new Book("Blade", BigDecimal.valueOf(115));

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        System.out.println(bookDao.findAll());

        bookDao.delete(book2.getId());

        book1.setTitle("SonySon-2");
        book1.setPrice(BigDecimal.valueOf(200));
        bookDao.update(book1);
        System.out.println(bookDao.findById(book1.getId()));
    }
}
