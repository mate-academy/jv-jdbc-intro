package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao carDao = (BookDao) injector.getInstance(BookDao.class);
    public static void main(String[] args) {
        Book harryPotter = new Book("Harry Potter", BigDecimal.valueOf(150));
        Book witcher = new Book("The Witcher", BigDecimal.valueOf(200));

        carDao.create(harryPotter);
        carDao.create(witcher);

        System.out.println(carDao.findAll());
        System.out.println(carDao.findById(2L));

        harryPotter.setTitle("Harry Potter 2");
        System.out.println(carDao.update(harryPotter));

        System.out.println(carDao.deleteById(2L));
    }
}
