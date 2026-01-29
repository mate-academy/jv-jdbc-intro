package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Title1");
        book.setPrice(BigDecimal.valueOf(100));
        bookDao.create(book);
        System.out.println("Created: " + book);

        Book book2 = new Book();
        book2.setTitle("Title2");
        book2.setPrice(BigDecimal.valueOf(200));
        bookDao.create(book2);
        System.out.println("Created: " + book2);

        Book book3 = new Book();
        book3.setTitle("Title3");
        book3.setPrice(BigDecimal.valueOf(300));
        bookDao.create(book3);
        System.out.println("Created: " + book3);

        bookDao.findById(book.getId())
                .ifPresent(b -> System.out.println("Found: " + b));

        book2.setPrice(BigDecimal.valueOf(150));
        bookDao.update(book2);

        bookDao.findById(book2.getId())
                .ifPresent(b -> System.out.println("Updated: " + b));

        boolean deleted = bookDao.deleteById(book3.getId());

        System.out.println("Book3 was deleted: " + deleted);

        bookDao.findAll().forEach(System.out::println);
    }
}
