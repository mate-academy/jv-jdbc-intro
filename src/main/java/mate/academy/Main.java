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
        book.setTitle("Clean Code");
        book.setPrice(new BigDecimal("99.99"));
        bookDao.create(book);

        System.out.println("All books: " + bookDao.findAll());

        book.setPrice(new BigDecimal("79.99"));
        bookDao.update(book);

        System.out.println("Book by ID: " + bookDao.findById(book.getId()));

        bookDao.deleteById(book.getId());
        System.out.println("Books after delete: " + bookDao.findAll());
    }
}
