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
        book1.setTitle("Lord of the Rings");
        book1.setPrice(BigDecimal.valueOf(400));
        bookDao.create(book1);

        Book book2 = new Book();
        book2.setTitle("Harry Potter");
        book2.setPrice(BigDecimal.valueOf(500));
        bookDao.create(book2);

        bookDao.findAll().forEach(System.out::println);
        System.out.println(bookDao.findById(book1.getId()));

        book2.setPrice(BigDecimal.valueOf(800));
        bookDao.update(book2);
        bookDao.deleteById(book1.getId());
    }
}
