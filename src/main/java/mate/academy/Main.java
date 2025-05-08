package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book(7L,"Mermaid", new BigDecimal(100));
        book1.setPrice(BigDecimal.valueOf(150));
        bookDao.update(book1);
        System.out.println(bookDao.findAll());
    }
}
