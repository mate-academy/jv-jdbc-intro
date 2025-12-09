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
        BigDecimal decimal = new BigDecimal("150");
        book.setTitle("How beautiful world");
        book.setPrice(decimal);
        System.out.println(bookDao.create(book));
        System.out.println(bookDao.deleteById(1L));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.findById(10L));
        System.out.println(bookDao.findAll());
    }
}
