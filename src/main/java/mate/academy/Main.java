package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = bookDao.create(new Book("Harry Potter and the Philosopher's Stone",
                new BigDecimal("10.45")));
        Book book2 = bookDao.create(new Book("Harry Potter And The Chamber of secrets",
                new BigDecimal("17.22")));
        Book book3 = bookDao.create(new Book("Harry Potter and the prisoner of Azkaban",
                new BigDecimal("20.15")));
        System.out.println(bookDao.findById(book1.getId()));

        System.out.println(bookDao.update(book3));

        System.out.println();
        book3.setPrice(new BigDecimal("10.56"));

        bookDao.deleteById(2L);
        System.out.println();

        bookDao.findAll().forEach(System.out::println);
    }
}
