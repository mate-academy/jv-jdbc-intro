package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Psychology of Influence", BigDecimal.valueOf(10L));
        Book book2 = new Book("Atlas", BigDecimal.valueOf(20L));
        Book book3 = new Book("Find an elephant", BigDecimal.valueOf(30L));
        bookDao.create(book1);
        bookDao.create(book2);
        bookDao.create(book3);
        bookDao.findAll();

        bookDao.findById(book1.getId());
        book1.setTitle("Stan Lee");
        bookDao.update(book1);

        bookDao.deleteById(book3.getId());
    }
}
