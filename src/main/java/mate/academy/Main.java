package mate.academy;

import java.math.BigDecimal;
import mate.academy.lib.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("GCSE Computer Science OCR Complete Revision");
        book.setPrice(BigDecimal.valueOf(400));
        bookDao.create(book);
        System.out.println(bookDao.findById(6L));
        System.out.println(bookDao.deleteById(6L));
        System.out.println(bookDao.findAll().toString());
        System.out.println(bookDao.update(book));
    }
}
