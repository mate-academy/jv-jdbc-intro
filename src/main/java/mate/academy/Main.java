package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book mateBook = new Book();
        mateBook.setTitle("Mate Academy");
        mateBook.setPrice(BigDecimal.valueOf(359));
        Book mateBook2 = new Book();
        mateBook2.setTitle("Mate Academy #2");
        mateBook2.setPrice(BigDecimal.valueOf(459));
        bookDao.create(mateBook);
        bookDao.create(mateBook2);
        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.findById(1L));
        Book bookToUpdate = new Book();
        bookToUpdate.setId(1L);
        bookToUpdate.setTitle("Java");
        bookToUpdate.setPrice(BigDecimal.valueOf(99));
        System.out.println(bookDao.update(bookToUpdate));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.deleteById(1L));
    }
}
