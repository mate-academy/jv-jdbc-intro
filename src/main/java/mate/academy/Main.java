package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("test2");
        book.setPrice(BigDecimal.valueOf(12345.56));
        bookDao.create(book);

        System.out.println(bookDao.findById(1L));

        Book book1 = new Book();
        book1.setTitle("test2");
        book1.setPrice(BigDecimal.valueOf(54321.56));
        book1.setId(1L);
        bookDao.update(book1);

        bookDao.findAll().forEach(System.out::println);

        bookDao.deleteById(1L);
    }
}
