package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book bookOne = new Book();
        bookOne.setTitle("Java for Beginners");
        bookOne.setPrice(BigDecimal.valueOf(340));

        Book bookTwo = new Book("The Complete Stories of Sherlock Holmes", BigDecimal.valueOf(700));

        Book bookThree = new Book("The Stainless Steel Rat", BigDecimal.valueOf(250));

        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        bookDao.create(bookThree);

        System.out.println(bookDao.findAll());

        System.out.println(bookDao.findById(4L));

        System.out.println(bookDao.deleteById(2L));

        bookOne.setPrice(BigDecimal.valueOf(370));

        System.out.println(bookDao.update(bookOne));
    }
}
