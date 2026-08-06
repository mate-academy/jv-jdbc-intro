package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("Clean Code");
        book1.setPrice(new BigDecimal("50.00"));
        bookDao.create(book1);
        System.out.println(bookDao.findById(1L));
        Book book2 = new Book();
        book2.setTitle("Thinking in Java");
        book2.setPrice(new BigDecimal("70.00"));
        bookDao.create(book2);
        System.out.println(bookDao.findAll());
        Book bookForUpdate = new Book();
        bookForUpdate.setId(2L);
        bookForUpdate.setTitle("Thinking in Java");
        bookForUpdate.setPrice(new BigDecimal("100.00"));
        System.out.println(bookDao.update(bookForUpdate));
        System.out.println(bookDao.deleteById(1L));
    }
}
