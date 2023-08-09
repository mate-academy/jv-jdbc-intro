package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;

public class Main {
    private static final Injector injector =
            Injector.getInstance("mate.academy");
    private static final BookDao bookDao =
            (BookDao) injector.getInstance(BookDao.class);
    public static void main(String[] args) {
        // Create a book
        Book firstBook = new Book();
        firstBook.setTitle("Anna Maria");
        firstBook.setPrice(BigDecimal.valueOf(100));
        Book secondBook = new Book();
        secondBook.setTitle("Mark Tween");
        secondBook.setPrice(BigDecimal.valueOf(85));
        // work with DB
        bookDao.create(firstBook);
        bookDao.create(secondBook);

        System.out.println(bookDao.findById(1L));

        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(2L);

        System.out.println(bookDao.update(secondBook));

    }
}
