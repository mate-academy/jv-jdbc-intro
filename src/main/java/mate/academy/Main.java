package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        String firstTitle = "three musketeers";
        BigDecimal firstPrice = new BigDecimal("44.5");
        Book firstBook = new Book();
        firstBook.setTitle(firstTitle);
        firstBook.setPrice(firstPrice);
        System.out.println(bookDao.create(firstBook));
        System.out.println(bookDao.findById(firstBook.getId()));
        String updatedTitle = "pride and prejudice";
        BigDecimal updatedPrice = new BigDecimal("55.0");
        Book updatedBook = new Book();
        updatedBook.setTitle(updatedTitle);
        updatedBook.setPrice(updatedPrice);
        updatedBook.setId(firstBook.getId());
        System.out.println(bookDao.update(updatedBook));
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.deleteById(updatedBook.getId()));
    }
}
