package mate.academy;

import java.math.BigDecimal;
import java.util.Optional;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("COLOBOK");
        book.setPrice(BigDecimal.valueOf(123));

        bookDao.create(book);
        Optional<Book> byId = bookDao.findById(2L);
        Book book1 = byId.get();
        book1.setPrice(BigDecimal.valueOf(555));
        bookDao.update(book1);
        System.out.println(bookDao.findAll());
        bookDao.deleteById(3L);
        System.out.println(bookDao.findAll());
    }
}
