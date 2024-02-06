package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        List<Book> all = bookDao.findAll();
        all.forEach(System.out::println);
        System.out.println(bookDao.findById(4L));
        Book book = new Book();
        book.setTitle("King Artur");
        book.setPrice(new BigDecimal(1500));
        bookDao.create(book);
        System.out.println(bookDao.deleteById(5L));
    }
}
