package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    public static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book mist = new Book();
        mist.setTitle("Mist");
        mist.setPrice(BigDecimal.valueOf(100));

        bookDao.create(mist);
        mist.setTitle("Mist by S.King");
        mist.setPrice(BigDecimal.valueOf(101));
        bookDao.update(mist);
        System.out.println(bookDao.findById(mist.getId()));
        bookDao.findAll().forEach(System.out::println);
        System.out.println("delete successful: " + bookDao.deleteById(mist.getId()));
    }
}
