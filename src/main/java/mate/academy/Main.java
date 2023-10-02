package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

import java.math.BigDecimal;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book();
        book1.setTitle("La vache");
        book1.setPrice(BigDecimal.valueOf(135));

        Book book2 = new Book();
        book2.setTitle("English Grammar");
        book2.setPrice(BigDecimal.valueOf(100));

        Book book3 = new Book();
        book3.setTitle("German Grammar");
        book3.setPrice(BigDecimal.valueOf(140));

        Book book4 = new Book();
        book4.setTitle("French Grammar");
        book4.setPrice(BigDecimal.valueOf(140));

        bookDao.create(book3);
        bookDao.deleteById(1L);
        bookDao. findById(2L);
        bookDao.findAll().forEach(System.out::println);
    }
}
