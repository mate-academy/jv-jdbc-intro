package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        //Testing create method
        Book book = new Book();
        book.setPrice(new BigDecimal(100));
        book.setTitle("Harry_Potter");
        Book createdBook = bookDao.create(book);
        System.out.println(createdBook);
        //Testing deleteById method
        bookDao.deleteById(createdBook.getId());
        //Testing findAll method
        Book secondCreatedBook = bookDao.create(book);
        Book book1 = new Book();
        book1.setTitle("Twilight");
        book1.setPrice(BigDecimal.valueOf(999));
        bookDao.create(book1);
        bookDao.findAll().forEach(System.out::println);
        //Testing update method
        Book book2 = new Book();
        book2.setId(secondCreatedBook.getId());
        book2.setTitle("Indiana Johns");
        book2.setPrice(BigDecimal.valueOf(200));
        bookDao.update(book2);
        //Testing find by id
        System.out.println(bookDao.findById(secondCreatedBook.getId()));
    }
}
