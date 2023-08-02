package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDaoDao =
            (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book pratchett = new Book();
        pratchett.setTitle("Colour of magic");
        pratchett.setPrice(new BigDecimal(300));

        Book cook = new Book();
        cook.setTitle("The Black Company");
        cook.setPrice(new BigDecimal(400));

        Book lawrence = new Book();
        lawrence.setTitle("Prince of Thorns");
        lawrence.setPrice(new BigDecimal(450));

        bookDaoDao.create(pratchett);
        bookDaoDao.create(cook);
        bookDaoDao.create(lawrence);

        bookDaoDao.getAll().forEach(System.out::println);

        pratchett.setTitle("Wyrd Sisters");
        pratchett.setPrice(new BigDecimal(350));
        bookDaoDao.update(pratchett);
        bookDaoDao.delete(lawrence.getId());

        bookDaoDao.getAll().forEach(System.out::println);
    }
}
