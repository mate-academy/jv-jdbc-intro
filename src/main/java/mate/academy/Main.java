package mate.academy;

import mate.academy.lib.Injector;
import mate.academy.dao.BookDao;
import mate.academy.model.Book;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book lordOfTheRing = new Book();
        lordOfTheRing.setTitle("Lord of the Ring");
        lordOfTheRing.setPrice(BigDecimal.valueOf(800));
        Book lordOfSecret = new Book();
        lordOfSecret.setTitle("Lord of Secret");
        lordOfSecret.setPrice(BigDecimal.valueOf(600));

        lordOfSecret = bookDao.create(lordOfSecret);
        lordOfTheRing = bookDao.create(lordOfTheRing);
        System.out.println(lordOfSecret);
        System.out.println(lordOfTheRing);

        List<Book> books = bookDao.findAll();
        books.forEach(System.out::println);

        lordOfSecret.setPrice(BigDecimal.valueOf(1000));
        lordOfSecret.setTitle("Lord of Secrets");
        lordOfSecret = bookDao.update(lordOfSecret);
        System.out.println(lordOfSecret);
        System.out.println(lordOfTheRing);

        boolean expectedTrue = bookDao.deleteById(1L);
        boolean expectedFalse = bookDao.deleteById(10L);
        System.out.println("expectedTrue = " + expectedTrue);
        System.out.println("expectedFalse = " + expectedFalse);

    }
}
