package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        DbInit.initialize();

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Birds of America");
        book.setPrice(BigDecimal.valueOf(256.00));
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Carrie");
        book2.setPrice(BigDecimal.valueOf(300.00));

        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        System.out.println(bookDao.create(book) + System.lineSeparator());
        System.out.println(bookDao.create(book2) + System.lineSeparator());
        System.out.println(bookDao.findById(2L) + System.lineSeparator());
        System.out.println(bookDao.findAll() + System.lineSeparator());
        System.out.println(bookDao.update(new Book(1L, "Birds of America.Extended version",
                BigDecimal.valueOf(310)))
                + System.lineSeparator());
        System.out.println(bookDao.deleteById(2L));
    }
}
