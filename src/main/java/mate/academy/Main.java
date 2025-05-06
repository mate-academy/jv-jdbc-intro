package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Book book1 = new Book();
        book1.setTitle("Clean Code");
        book1.setPrice(BigDecimal.valueOf(650));

        Book book2 = new Book();
        book2.setTitle("Java for professionals");
        book2.setPrice(BigDecimal.valueOf(1200));

        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookCleanCode = bookDao.create(book1);
        Book bookJavaForProfessionals = bookDao.create(book2);

        System.out.println(bookDao.findById(bookCleanCode.getId()).get());
        System.out.println(bookDao.findById(bookJavaForProfessionals.getId()).get());

        System.out.println(bookDao.findAll());

        book1.setPrice(BigDecimal.valueOf(1000));
        bookDao.update(book1);

        System.out.println(bookDao.deleteById(18L));
    }
}
