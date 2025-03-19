package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate/academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book();
        book.setTitle("Zibibibi");
        book.setPrice(new BigDecimal(450));

        System.out.println(bookDao.create(book));

        System.out.println(bookDao.findById(book.getId()));

        System.out.println(bookDao.findAll());

        book.setTitle("Gugigiga");
        book.setPrice(new BigDecimal(400));
        System.out.println(bookDao.update(book));

        System.out.println(bookDao.deleteById(1L));
    }
}
