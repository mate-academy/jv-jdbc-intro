package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.entity.Book;
import mate.academy.lib.Injector;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book coreJavaVol1 = new Book();
        coreJavaVol1.setTitle("Core Java: Fundamentals, Volume 1");
        coreJavaVol1.setPrice(new BigDecimal("50.59"));

        Book coreJavaVol2 = new Book();
        coreJavaVol2.setTitle("Core Java: Advanced Features, Volume 2");
        coreJavaVol2.setPrice(new BigDecimal("48.38"));

        Book javaSE8 = new Book();
        javaSE8.setTitle("Java SE 8. Programmer I Exam Guide");
        javaSE8.setPrice(new BigDecimal("35.99"));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        // create
        System.out.println(bookDao.create(coreJavaVol1));
        System.out.println(bookDao.create(coreJavaVol2));
        System.out.println(bookDao.create(javaSE8));

        // findById
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findById(2L));
        System.out.println(bookDao.findById(3L));

        // findAll
        System.out.println(bookDao.findAll());

        // update
        coreJavaVol1.setId(1L);
        coreJavaVol1.setPrice(new BigDecimal(60));
        System.out.println(bookDao.update(coreJavaVol1));

        // deleteById
        System.out.println(bookDao.delete(1L));

    }
}
