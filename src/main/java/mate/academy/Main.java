package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    private static BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book book = new Book();
        book.setTitle("Kobzzar");
        book.setPrice(BigDecimal.valueOf(300));
        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(1L));
    }
}
