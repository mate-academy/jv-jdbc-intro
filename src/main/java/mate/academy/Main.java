package mate.academy;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book1 = new Book("Book1", new BigDecimal("23.59"));
        Book book2 = new Book("Book2", new BigDecimal("33.59"));
        Book book3 = new Book("Book2", new BigDecimal("43.59"));

        Book bookCreate1 = bookDao.create(book1);
        Book bookCreate2 = bookDao.create(book2);
        Book bookCreate3 = bookDao.create(book3);

        List<Book> all = bookDao.findAll();
        Optional<Book> byId = bookDao.findById(book1.getId());
        Book update = bookDao.update(book1);
        boolean b = bookDao.deleteById(book1.getId());
    }
}
