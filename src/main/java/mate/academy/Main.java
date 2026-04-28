package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        //  C
        Book book1 = new Book();
        book1.setTitle("Gandon");
        book1.setPrice(BigDecimal.valueOf(123));
        System.out.println(bookDao.create(book1));

        Book book2 = new Book();
        book2.setTitle("Gandon2");
        book2.setPrice(BigDecimal.valueOf(123));
        System.out.println(bookDao.create(book2));

        // R
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findAll());

        // U
        Book book3 = new Book();
        book3.setId(1L);
        book3.setTitle("Gandon10Update");
        book3.setPrice(BigDecimal.valueOf(123));
        System.out.println(bookDao.update(book3));

        // D
        System.out.println(bookDao.deleteById(4L));
    }
}
