package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInstance("mate.academy");
        BookDao dao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Garry Potter");
        book.setPrice(BigDecimal.valueOf(25.4));
        System.out.println(dao.create(book));
    }
}
