package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        System.out.println(bookDao.createBooksTable());
        Book book = new Book();
        book.setTitle("harry potter");
        book.setPrice(BigDecimal.valueOf(124));
        bookDao.create(book);
        System.out.println(bookDao.findById(1L));
        System.out.println(bookDao.findAll());
        book.setPrice(BigDecimal.valueOf(300));
        System.out.println(bookDao.update(book));
        book.setTitle("Game of Thrones");
        book.setPrice(BigDecimal.valueOf(200));
        System.out.println(bookDao.create(book));
        System.out.println(bookDao.deleteById(1L));
    }
}
