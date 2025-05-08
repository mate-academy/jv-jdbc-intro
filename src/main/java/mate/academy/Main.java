package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book bookOne = new Book("test1", new BigDecimal("100.99"));
        Book bookTwo = new Book("test2", new BigDecimal("300.90"));
        Book bookThree = new Book("test3", new BigDecimal("220.00"));
        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        Book createdBookThree = bookDao.create(bookThree);
        bookDao.findAll().forEach(System.out::println);
        bookDao.findById(createdBookThree.getId());
        Book bookForUpdate = new Book();
        bookForUpdate.setTitle("new");
        bookForUpdate.setId(3L);
        bookForUpdate.setPrice(new BigDecimal("150.00"));
        bookDao.update(bookForUpdate);
        bookDao.deleteById(bookForUpdate.getId());
    }
}
