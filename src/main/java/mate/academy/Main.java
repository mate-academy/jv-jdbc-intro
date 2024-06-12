package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book(1L,"Test book", BigDecimal.valueOf(10));
        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        Book bookToUpdate = new Book(1L,"Test book2", BigDecimal.valueOf(20));
        bookToUpdate.setId(1L);
        System.out.println(bookDao.update(bookToUpdate));
        System.out.println(bookDao.deleteById(1L));
    }
}
