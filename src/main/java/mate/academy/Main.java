package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        bookDao.findAll().forEach(System.out::println);
        System.out.println();

        Book book = new Book();
        book.setTitle("title4");
        book.setPrice(new BigDecimal(300));
        System.out.println(bookDao.create(book));
        System.out.println(bookDao.findById(1L));
        book.setTitle("updatedTitle");
        System.out.println(bookDao.update(book));

        System.out.println();
        bookDao.deleteById(4L);
        bookDao.findAll().forEach(System.out::println);

    }
}
