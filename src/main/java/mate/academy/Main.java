package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) INJECTOR.getInstance(BookDao.class);
        Book bookOne = new Book("Fight Club", new BigDecimal("199.99"));
        Book bookTwo = new Book("Clean Code", new BigDecimal("329.90"));
        Book bookThree = new Book("Atomic habits", new BigDecimal("220.00"));
        //create
        bookDao.create(bookOne);
        bookDao.create(bookTwo);
        Book createdBookThree = bookDao.create(bookThree);
        //read
        bookDao.findAll().forEach(System.out::println);
        bookDao.findById(createdBookThree.getId());
        //update
        Book bookForUpdate = new Book();
        bookForUpdate.setTitle("Updated Title");
        bookForUpdate.setId(3L);
        bookForUpdate.setPrice(new BigDecimal("150.00"));
        bookDao.update(bookForUpdate);
        //delete
        bookDao.deleteById(bookForUpdate.getId());
    }
}
