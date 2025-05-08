package mate.academy.lib.service;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.lib.dao.BookDao;
import mate.academy.lib.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.lib");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book airport = new Book("Airport", BigDecimal.valueOf(29.00));
        bookDao.create(airport);
        System.out.println(airport);

        System.out.println(bookDao.findById(2L));

        airport.setTitle("Waterport");
        System.out.println(bookDao.update(airport));

        bookDao.deleteById(1L);

        System.out.println(bookDao.findAll());
    }
}
