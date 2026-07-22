package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy"); // перевірка
    // чи існує інжектор для даного пакета, якщо ні - під капотом створ новий інжектор і дод в мапу

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book firstBook = new Book();
        firstBook.setTitle("First Book");
        firstBook.setPrice(BigDecimal.valueOf(50.90));

        Book secondBook = new Book();
        secondBook.setTitle("Second Book");
        secondBook.setPrice(BigDecimal.valueOf(28.80));

        Book thirdBook = new Book();
        thirdBook.setTitle("Third Book");
        thirdBook.setPrice(BigDecimal.valueOf(34.99));

        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.create(thirdBook);

        System.out.println(bookDao.findAll());

        /*
        System.out.println(bookDao.deleteById(3L));
        System.out.println(bookDao.findById(2L));

        secondBook = new Book();
        secondBook.setTitle("updated Book");
        secondBook.setPrice(BigDecimal.valueOf(999.999));
        secondBook.setId(2L);

        System.out.println(bookDao.update(secondBook));
        */
    }
}
