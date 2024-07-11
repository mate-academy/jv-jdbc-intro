package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book harryPotter = new Book();
        harryPotter.setTitle("Harry Potter");
        harryPotter.setPrice(BigDecimal.valueOf(25));
        Book lordOfTheRings = new Book();
        lordOfTheRings.setTitle("Lord of the Rings");
        lordOfTheRings.setPrice(BigDecimal.valueOf(15));
        Book harryPotterBook = bookDao.create(harryPotter);
        Book lotrBook = bookDao.create(lordOfTheRings);
        System.out.println(harryPotterBook);
        System.out.println(lotrBook);
        bookDao.findAll().forEach(System.out::println);
        harryPotter.setPrice(BigDecimal.valueOf(50));
        bookDao.update(harryPotter);

    }
}
