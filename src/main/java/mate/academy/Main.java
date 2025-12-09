package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("Game of thrones", BigDecimal.valueOf(100));
        Book book2 = new Book("Harry Potter", BigDecimal.valueOf(200));
        Book book3 = new Book("The Lord of the Rings", BigDecimal.valueOf(300));

        //Create
        System.out.println(bookDao.create(book1));
        System.out.println(bookDao.create(book2));
        System.out.println(bookDao.create(book3));

        //Find
        System.out.println(bookDao.findById(book1.getId()));
        System.out.println(bookDao.findById(book2.getId()));
        System.out.println(bookDao.findById(book3.getId()));
        System.out.println(bookDao.findAll());

        //Update
        book1.setPrice(BigDecimal.valueOf(1000));
        System.out.println(bookDao.update(book1));
        book2.setTitle("Harry potter and the philosopher's stone");
        book2.setPrice(BigDecimal.valueOf(2000));
        System.out.println(bookDao.update(book2));
        System.out.println(bookDao.findAll());

        //Delete
        System.out.println(bookDao.deleteById(book2.getId()));
        System.out.println(bookDao.deleteById(book3.getId()));
        System.out.println(bookDao.findAll());
    }
}
