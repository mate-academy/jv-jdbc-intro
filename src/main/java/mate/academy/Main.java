package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        Book book1 = new Book();
        book1.setTitle("Book Title1");
        book1.setPrice(BigDecimal.valueOf(1.1));
        Book book2 = new Book();
        book2.setTitle("Book Title2");
        book2.setPrice(BigDecimal.valueOf(2.2));
        Book book3 = new Book();
        book3.setTitle("Book Title3");
        book3.setPrice(BigDecimal.valueOf(3.3));

        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);

        System.out.println(bookDao.findById(book1.getId()));
        System.out.println(bookDao.findById(book2.getId()));
        System.out.println(bookDao.findById(book3.getId()));

        book1.setTitle("Book Title11");
        book1.setPrice(BigDecimal.valueOf(11.11));
        book2.setTitle("Book Title22");
        book2.setPrice(BigDecimal.valueOf(22.22));
        book3.setTitle("Book Title33");
        book3.setPrice(BigDecimal.valueOf(33.33));
        bookDao.update(book1);
        bookDao.update(book2);
        bookDao.update(book3);

        System.out.println(bookDao.findAll());

        bookDao.deleteById(book1.getId());
        System.out.println(bookDao.findAll());
    }
}
