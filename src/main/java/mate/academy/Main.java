package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book = new Book("title1", BigDecimal.valueOf(1));
        Book book2 = new Book("title12", BigDecimal.valueOf(12));
        Book book3 = new Book("title123", BigDecimal.valueOf(123));
        final Book book4 = new Book("title1234", BigDecimal.valueOf(1234));

        System.out.println(System.lineSeparator() + "TEST OF CREATE" + System.lineSeparator());
        final Book book5 = bookDao.create(book);
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.create(book4);

        System.out.println(System.lineSeparator() + "TEST OF FIND ALL #1" + System.lineSeparator());
        for (Book b: bookDao.findAll()) {
            System.out.println(b);
        }

        System.out.println(System.lineSeparator() + "TEST OF UPDATE" + System.lineSeparator());
        book5.setPrice(BigDecimal.valueOf(999));
        System.out.println(bookDao.update(book5));

        System.out.println(System.lineSeparator() + "TEST OF DELETE BY ID"
                + System.lineSeparator());
        System.out.println(bookDao.deleteById(3L));

        System.out.println(System.lineSeparator() + "TEST OF FIND BY ID" + System.lineSeparator());
        System.out.println(bookDao.findById(4L));

        System.out.println(System.lineSeparator() + "TEST OF FIND ALL #2" + System.lineSeparator());
        for (Book b: bookDao.findAll()) {
            System.out.println(b);
        }
    }
}
