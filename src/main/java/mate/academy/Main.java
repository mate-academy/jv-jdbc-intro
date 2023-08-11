package mate.academy;

import mate.academy.dao.BookDao;
import mate.academy.lib.Injector;
import mate.academy.model.Book;
import java.math.BigDecimal;
import java.util.Optional;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.academy.dao");

    public static void main(String[] args) {
        BookDao bookDao = (BookDao) injector.getInstance(BookDao.class);
        Book book = new Book();
        book.setTitle("Sample");
        book.setPrice(BigDecimal.valueOf(5.99));
        bookDao.create(book);
        Long bookId = 1L;
        Optional<Book> foundBook = bookDao.findById(bookId);
        bookDao.findAll();
        Book updatedBook = foundBook.orElseThrow();
        bookDao.update(updatedBook);
        bookDao.deleteById(bookId);
    }
}
