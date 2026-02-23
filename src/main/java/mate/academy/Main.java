package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Zbrodnia i Kara");
        book.setPrice(new BigDecimal("99.99"));
        bookDao.create(book);

        book.setTitle("Zbrodnia i Kara II");
        bookDao.update(book);
    }

}
