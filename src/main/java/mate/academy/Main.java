package mate.academy;

import java.math.BigDecimal;
import mate.academy.bookdao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        Book book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Java");
        book1.setPrice(new BigDecimal("5.55"));
        BookDao bookDao = (BookDao) injector
                .getInstance(BookDao.class);
        bookDao.create(book1);
        Book book2 = new Book();
        book2.setId(2L);
        book2.setTitle("C++");
        book2.setPrice(new BigDecimal("6.66"));
        bookDao.create(book2);
        System.out.println(bookDao.findById(book1.getId()));
        System.out.println(bookDao.findById(book2.getId()));

        book1.setPrice(new BigDecimal("7.77"));
        bookDao.update(book1);
        book2.setPrice(new BigDecimal("8.88"));
        bookDao.update(book2);

        System.out.println(bookDao.findAll());
        bookDao.deleteById(book1.getId());
    }
}
