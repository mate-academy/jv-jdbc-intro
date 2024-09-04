package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private BookDao bookDao;

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("The Perfect Shot", BigDecimal.valueOf(100));
        Book book2 = new Book("Modern Java", BigDecimal.valueOf(350));
        Book book3 = new Book("Atomic Habits", BigDecimal.valueOf(220));
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        System.out.println(bookDao.findAll());
        book3.setPrice(BigDecimal.valueOf(250));
        bookDao.update(book3);
        System.out.println(bookDao.findAll());
        System.out.println(bookDao.findById(2L).get());
        bookDao.deleteById(1L);
        System.out.println(bookDao.findAll());
    }
}
