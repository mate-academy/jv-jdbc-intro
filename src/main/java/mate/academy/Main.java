package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        //creating books
        Book book1 = new Book();
        book1.setTitle("Atomic Habits");
        book1.setPrice(BigDecimal.valueOf(55.99));
        Book book2 = new Book();
        book2.setTitle("Zero to One");
        book2.setPrice(BigDecimal.valueOf(61.50));
        Book book3 = new Book();
        book3.setTitle(" Think and Grow Rich");
        book3.setPrice(BigDecimal.valueOf(99.99));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.create(book1));
        System.out.println(bookDao.create(book2));
        System.out.println(bookDao.create(book3));
        //reading from DB
        System.out.println(bookDao.findById(1L));
        bookDao.findAll().stream().forEach(System.out::println);
        //updating data
        book3.setPrice(BigDecimal.valueOf(89.99));
        System.out.println(bookDao.update(book3));
        //deleting book from DB
        System.out.println(bookDao.deleteById(1L));
    }
}
