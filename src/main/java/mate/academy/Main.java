package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book firstBook = new Book();
        firstBook.setTitle("Harry Potter");
        firstBook.setPrice(BigDecimal.valueOf(700));
        bookDao.create(firstBook);
        Book secondBook = new Book();
        secondBook.setTitle("The little women");
        secondBook.setPrice(BigDecimal.valueOf(300));
        bookDao.create(firstBook);
        bookDao.create(secondBook);
        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.findById(1L));
        Book bookToUpdate = new Book();
        bookToUpdate.setId(1L);
        bookToUpdate.setTitle("After");
        bookToUpdate.setPrice(BigDecimal.valueOf(200));
        System.out.println(bookDao.update(bookToUpdate));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.deleteById(1L));
    }
}
