package mate.academy;

import java.math.BigInteger;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        // create
        Book book = new Book();
        book.setTitle("Java Programming");
        book.setPrice(100.00);
        System.out.println(bookDao.create(book));
        // update
        Book bookUpdate = new Book();
        bookUpdate.setTitle("Updated title");
        bookUpdate.setPrice(200.00);
        bookUpdate.setId(new BigInteger("1"));
        System.out.println(bookDao.update(bookUpdate));
        // findById
        System.out.println(bookDao.findById(2L));
        // findAll
        System.out.println(bookDao.findAll());
        // deleteById
        System.out.println(bookDao.deleteById(1L));
    }
}
