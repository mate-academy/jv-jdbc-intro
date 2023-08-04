package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book("Angel and Demon", new BigDecimal(400));
        bookDao.create(book);
        System.out.println(bookDao.findById(book.getId()));
        bookDao.findAll().forEach(System.out::println);
        book.setPrice(new BigDecimal(700));
        System.out.println(bookDao.update(book));
        System.out.println(bookDao.deleteById(book.getId()));
    }
}
