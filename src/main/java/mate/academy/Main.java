package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book whiteFang = new Book();
        whiteFang.setTitle("White Fang");
        whiteFang.setPrice(BigDecimal.valueOf(200));

        Book tracksOnTheRoad = new Book();
        tracksOnTheRoad.setTitle("Tracks on the road");
        tracksOnTheRoad.setPrice(BigDecimal.valueOf(380));

        Book theGatesOfEurope = new Book();
        theGatesOfEurope.setTitle("The gates of Europe");
        theGatesOfEurope.setPrice(BigDecimal.valueOf(350));

        bookDao.create(whiteFang);
        bookDao.create(tracksOnTheRoad);
        bookDao.create(theGatesOfEurope);

        System.out.println(bookDao.findById(1L).get());
        System.out.println(bookDao.findAll());

        theGatesOfEurope.setPrice(BigDecimal.valueOf(200));
        System.out.println(bookDao.update(theGatesOfEurope));

        System.out.println(bookDao.deleteById(tracksOnTheRoad.getId()));
    }
}
