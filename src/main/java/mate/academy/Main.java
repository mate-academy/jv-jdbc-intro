package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.dao.BookDaoImpl;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDaoImpl bookDao = (BookDaoImpl) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Moriae Encomium, sive Stultitiae Laus");
        book.setPrice(BigDecimal.valueOf(100));
        bookDao.create(book);
        System.out.println(bookDao.findById(1L).toString());
    }
}
