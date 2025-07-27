package mate.academy;

import java.math.BigDecimal;
import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy");

    public static void main(String[] args) {

        Book book = new Book();
        book.setId(1L);
        book.setTitle("Mate");
        book.setPrice(new BigDecimal(120));
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);

        bookDao.create(book);
        bookDao.update(book);
        bookDao.findAll().forEach(System.out::println);
        bookDao.findById(book.getId());
        bookDao.deleteById(book.getId());

    }
}
