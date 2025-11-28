package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.Injector;
import mate.academy.lib.model.Book;
import mate.academy.lib.model.BookDao;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy.lib");
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book bookC = new Book();
        bookC.setTitle("new title");
        bookC.setPrice(BigDecimal.valueOf(47.34));
        for (int i = 0; i < 5; i++) {
            bookDao.create(bookC);
        }
        System.out.println(bookDao.deleteById(4L));
        System.out.println(bookDao.findById(2L));
        Book book = new Book();
        book.setId(3L);
        book.setTitle("updated title");
        book.setPrice(BigDecimal.valueOf(77.34));
        System.out.println(bookDao.update(book));
        bookDao.findAll().forEach(System.out::println);
    }
}
