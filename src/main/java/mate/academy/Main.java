package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.dao.BookDao;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book createdBookOne = new Book();
        createdBookOne.setTitle("Kobzar");
        createdBookOne.setPrice(BigDecimal.valueOf(1000));
        bookDao.create(createdBookOne);

        Book createdBookTwo = new Book();
        createdBookTwo.setTitle("Python");
        createdBookTwo.setPrice(BigDecimal.valueOf(500));
        bookDao.create(createdBookTwo);

        bookDao.getById(1L);

        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(2L);
    }
}
