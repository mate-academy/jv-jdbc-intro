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
        book.setTitle("Sherlock Holmes");
        book.setPrice(BigDecimal.valueOf(110L));

        Book savedBook = bookDao.create(book);
        Optional<Book> bookById = bookDao.findById(savedBook.getId());
        System.out.println(bookById);
        System.out.println(bookDao.findAll());

        book.setPrice(BigDecimal.valueOf(371L));
        bookDao.update(book);
        bookDao.findById(savedBook.getId());
        bookDao.deleteById(savedBook.getId());
        System.out.println(bookDao.findAll());
    }
}
