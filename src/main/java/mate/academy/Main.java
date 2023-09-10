package mate.academy;

import java.util.List;
import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setPrice(BigDecimal.valueOf(225));
        book.setTitle("python");
        bookDao.create(book);
        List<Book> all = bookDao.findAll();
        System.out.println(all.toString());
        boolean b = bookDao.deleteById(4L);
        System.out.println(b);
        Book book1 = new Book();
        book1.setTitle("DKD++");
        book1.setPrice(BigDecimal.valueOf(500));
        book1.setId(2L);
        bookDao.update(book1);
        List<Book> all2 = bookDao.findAll();
        System.out.println(all2.toString());
    }
}
