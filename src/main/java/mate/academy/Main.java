package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");
    private static final BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

    public static void main(String[] args) {
        Book firstBook = new Book();
        firstBook.setTitle("name1");
        firstBook.setPrice(BigDecimal.valueOf(100));
        bookDao.create(firstBook);
        System.out.println(bookDao.findById(7L));
        System.out.println(bookDao.findAll());
        Book secondBook = new Book();
        secondBook.setId(7L);
        secondBook.setTitle("name2");
        secondBook.setPrice(BigDecimal.valueOf(50));
        System.out.println(bookDao.update(secondBook));
        bookDao.deleteById(firstBook.getId() - 1);
    }
}
