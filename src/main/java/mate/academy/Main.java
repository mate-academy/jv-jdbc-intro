package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book("Book", BigDecimal.valueOf(100));
        System.out.println("Book 1: " + bookDao.create(book1));

        Book book2 = new Book("Book 2", BigDecimal.valueOf(200));
        System.out.println("Book 2: " + bookDao.create(book2));

        book1.setPrice(BigDecimal.valueOf(210));
        System.out.println(bookDao.update(book1));
        System.out.println(bookDao.findById(book1.getId()));

        bookDao.findAll().forEach(System.out::println);

        System.out.println(bookDao.deleteById(book2.getId()));
    }
}
