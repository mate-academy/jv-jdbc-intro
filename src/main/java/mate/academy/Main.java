package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("YOUR_PACKAGE");

    public static void main(String[] args) {
        BookDao bookDao = new BookDaoImpl();

//        Book lordOfTheRing = new Book();
//        lordOfTheRing.setTitle("Lord of the ring");
//        lordOfTheRing.setPrice(BigDecimal.valueOf(999));
//        bookDao.create(lordOfTheRing);
//
//        Book mainCamp = new Book();
//        mainCamp.setTitle("Main camp");
//        mainCamp.setPrice(BigDecimal.valueOf(1));
//        bookDao.create(mainCamp);
//
//        List<Book> all = bookDao.findAll();
//        System.out.println(all);

        Optional<Book> byId = bookDao.findById(1l);
        System.out.println(byId);
    }
}
