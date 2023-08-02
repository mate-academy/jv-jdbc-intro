package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao =
                (BookDao) injector.getInstance(BookDao.class);
        Book kobzar = new Book(
                "Kobzar",
                BigDecimal.valueOf(599));
        Book eneida = new Book(
                "Eneida",
                BigDecimal.valueOf(259));
        Book mavka = new Book(
                "Mavka",
                BigDecimal.valueOf(419));

        System.out.println("START TESTING:");
        System.out.println("BookService create method was called.");
        System.out.println(bookDao.create(kobzar));
        System.out.println(bookDao.create(eneida));
        System.out.println(bookDao.create(mavka));
        System.out.println("Done");
        System.out.println("BookService findById method was called.");
        System.out.println(bookDao.findById(eneida.getId()));
        System.out.println("Done");
        System.out.println("BookService update method was called.");
        eneida.setPrice(BigDecimal.valueOf(379));
        System.out.println(bookDao.update(eneida));
        System.out.println("Done");
        System.out.println("BookService delete method was called.");
        bookDao.deleteById(3L);
        System.out.println("Done");
        System.out.println("BookService findAll method was called.");
        System.out.println(bookDao.findAll());
        System.out.println("Done");
        System.out.println("FINISH TESTING:");
    }
}
