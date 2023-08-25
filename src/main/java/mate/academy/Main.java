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
        book1.setTitle("Book1");
        book1.setPrice(BigDecimal.valueOf(100));

        Book book1Created = bookDao.create(book1);
        System.out.println(bookDao.findById(book1Created.getId()).get());

        Book book2 = new Book();
        book2.setTitle("Book2");
        book2.setPrice(BigDecimal.valueOf(150));
        Book book2Created = bookDao.create(book2);
        System.out.println(bookDao.findById(book2Created.getId()).get());

        book2.setTitle("Book3");
        bookDao.update(book2);
        bookDao.deleteById(book1Created.getId());
        System.out.println(bookDao.findAll());
    }
}
