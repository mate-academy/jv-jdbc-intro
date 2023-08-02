package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Harry Potter", BigDecimal.valueOf(400));
        Book book2 = new Book("Harry Potter2", BigDecimal.valueOf(450));
        Book book3 = new Book("Harry Potter3", BigDecimal.valueOf(490));
        System.out.println("added " + bookDao.create(book));
        System.out.println("added " + bookDao.create(book2));
        System.out.println("added " + bookDao.create(book3));
        System.out.println("all books: " + bookDao.findAll());
        book3.setTitle("Will");
        book3.setPrice(BigDecimal.valueOf(300));
        System.out.println("updated " + bookDao.update(book3));
        System.out.println("all books with update " + bookDao.findAll());
        System.out.println("find by id " + bookDao.findById(3L));
        bookDao.deleteById(3l);
        System.out.println("all books after delete " + bookDao.findAll());
    }
}
