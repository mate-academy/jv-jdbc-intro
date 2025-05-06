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
        Book newBook = new Book("Harry Potter and The Chamber of Secrets", new BigDecimal(123));
        bookDao.create(newBook);
        newBook = new Book("Lord of The Rings", new BigDecimal(345));
        bookDao.create(newBook);
        Optional<Book> bookFromDB = bookDao.findById(newBook.getId());
        bookFromDB.ifPresent(book -> {
            book.setTitle("Harry Potter and The Goblet of Fire");
            book.setPrice(new BigDecimal(234));
            bookDao.update(bookFromDB.get());
        });
        bookDao.deleteById(newBook.getId());
        List<Book> books = bookDao.findAll();
    }
}
